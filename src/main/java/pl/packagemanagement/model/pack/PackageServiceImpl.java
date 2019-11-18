package pl.packagemanagement.model.pack;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.code.CodeRepository;
import pl.packagemanagement.model.history.History;
import pl.packagemanagement.model.history.HistoryRepository;
import pl.packagemanagement.model.packagestatus.PackageStatus;
import pl.packagemanagement.model.packagestatus.PackageStatusRepository;
import pl.packagemanagement.model.user.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final PackageStatusRepository packageStatusRepository;
    private final CodeRepository codeRepository;
    private final HistoryRepository historyRepository;


    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<Package> findById(Long id) {
        return packageRepository.findById(id);
    }

    @Override
    public Optional<Package> findByNumber(String number) {
        return packageRepository.findByPackageNumber(number);
    }

    @Override
    public Page<Package> findByUsers(List<User> users, int pageNumber, int pageSize, String orderBy, String direction) {
        Page<Package> pagedPackage;
        if(direction.equals(Sort.Direction.ASC.name()))
            pagedPackage = packageRepository.findAllByUsers(users, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderBy)));
        else
            pagedPackage = packageRepository.findAllByUsers(users, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, orderBy)));

        return pagedPackage;
    }

    @Override
    public void delete(Package pack) {
        packageRepository.delete(pack);

    }


    @Override
    public Package save(Package pack, User user) {
        String generatedString = RandomStringUtils.random(13, false, true);

        if(packageRepository.findByPackageNumber(generatedString).isPresent())
            this.save(pack, user);

        pack = packChange(generatedString, pack, user); //przypisanie kodu, statusu, uzytkownika i numeru
        pack = packageRepository.save(pack); //zapis paczki do bazy
        pack.getCode().setPack(pack);
        History history = historyRepository.save(new History(null, "W oczekiwaniu na kuriera", LocalDateTime.now(ZoneId.of("Europe/Warsaw")), "U nadawcy", pack));
        pack.getHistories().add(history); //dodanie nowej historii do paczki

        try {
            String fileName = pack.getUsers().get(0).getLogin() + "-" + pack.getPackageNumber() + ".pdf";
            String path = "src/main/resources/documents/" + pack.getUsers().get(0).getLogin();
            String finalPath = path + "/" + fileName;
            createDocument(pack, path, finalPath); //tworzenie dokumentu pdf
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pack; //zwracamy paczke
    }


    @Override
    public Package update(Package pack) {
        Package tempPack = packageRepository.findById(pack.getId()).orElseThrow(() -> new EntityNotFoundException("Package not found"));
        PackageStatus packageStatus = packageStatusRepository.findById(pack.getPackageStatus().getId()).get();
        History tempHistory;
        if(tempPack.getPackageStatus().getId() != pack.getPackageStatus().getId()) {
            if(pack.getWarehouses().size() > 0)
                tempHistory = historyRepository.save(new History(null, packageStatus.getName(), LocalDateTime.now(ZoneId.of("Europe/Warsaw")), pack.getWarehouses().get(0).getCity(), tempPack));
            else{
                tempHistory = historyRepository.save(new History(null, packageStatus.getName(), LocalDateTime.now(ZoneId.of("Europe/Warsaw")), "U nadawcy", tempPack));
            }
            pack.getHistories().add(tempHistory);
            if(tempPack.getPackageStatus().getId() == 3)
                pack.setDate(LocalDateTime.now());
        }
        return packageRepository.save(pack);
    }


    private Package packChange(String random, Package pack, User user) {
        pack.getCode().setFilePath(random);
        Code code = codeRepository.save(pack.getCode());
        pack.setCode(code);
        PackageStatus packageStatus = packageStatusRepository.findById(3l).get();
        packageStatus.getPackages().add(pack);
        pack.setPackageStatus(packageStatus);
        pack.setPackageNumber(random);
        pack.getUsers().add(user);
        user.getPackages().add(pack);

        return pack;
    }

    private static void createDocument(Package pack, String filePath, String finalPath) throws Exception {
        Document document = new Document();

        BaseFont bf = BaseFont.createFont("FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font boldFont = new Font(bf, 12, Font.BOLD);
        Font boldFont16 = new Font(bf, 16, Font.BOLD); //czcionka pogrubiona
        Font normalFont = new Font(bf, 12, Font.NORMAL); //czcionka normalna

        File dir = new File(filePath);
        dir.mkdirs();
        FileOutputStream newFile = new FileOutputStream(finalPath);
        PdfWriter writer = PdfWriter.getInstance(document, newFile);
        document.open(); //otwarcie dokumentu


        //tabela trzymajaca tabele z danymi paczki i kod QR
        PdfPTable AllTable = new PdfPTable(2);
        AllTable.setWidthPercentage(100);
        AllTable.setSpacingBefore(10f);
        AllTable.setSpacingAfter(10f);

        //tabela trzymajaca dane paczki
        PdfPTable packageTable = new PdfPTable(2);
        packageTable.setWidthPercentage(40);
        packageTable.setSpacingBefore(10f);
        packageTable.setSpacingAfter(10f);
        packageTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        //ustawienia szerokosci kolumn dla danych paczki
        float[] columnWidths = {1.2f, 1.5f};
        packageTable.setWidths(columnWidths);

        PdfPTable senderTable = new PdfPTable(2);
        senderTable.setWidthPercentage(40);
        senderTable.setSpacingBefore(10f);
        senderTable.setSpacingAfter(10f);
        senderTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        senderTable.setWidths(columnWidths);

        PdfPTable recipientTable = new PdfPTable(2);
        recipientTable.setWidthPercentage(40);
        recipientTable.setSpacingBefore(10f);
        recipientTable.setSpacingAfter(10f);
        recipientTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        recipientTable.setWidths(columnWidths);

        //konfiguracja tabeli paczki
        PdfPCell titleHeader = new PdfPCell(new Paragraph("Dane paczki", boldFont16));
        titleHeader.setColspan(2);
        titleHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleHeader.setBorder(Rectangle.NO_BORDER);
        titleHeader.setPaddingBottom(25);

        PdfPCell packageNumberHeader = new PdfPCell(new Paragraph("Numer paczki:", boldFont));
        packageNumberHeader.setBorder(Rectangle.NO_BORDER);
        packageNumberHeader.setPaddingLeft(10);
        packageNumberHeader.setPaddingBottom(10);

        PdfPCell packageNumber = new PdfPCell(new Paragraph(pack.getPackageNumber()));
        packageNumber.setBorder(Rectangle.NO_BORDER); //brak obramowania

        PdfPCell packageSizeHeader = new PdfPCell(new Paragraph("WysxDłxSz[cm]:", boldFont));
        packageSizeHeader.setBorder(Rectangle.NO_BORDER);
        packageSizeHeader.setPaddingLeft(10);
        packageSizeHeader.setPaddingBottom(10);

        PdfPCell packageSize = new PdfPCell(new Paragraph(pack.getHeight() + "x" + pack.getLength() + "x" + pack.getWidth()));
        packageSize.setBorder(Rectangle.NO_BORDER);

        PdfPCell packageCommentsHeader = new PdfPCell(new Paragraph("Uwagi do paczki:", boldFont));
        packageCommentsHeader.setColspan(2);
        packageCommentsHeader.setBorder(Rectangle.NO_BORDER);
        packageCommentsHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        packageCommentsHeader.setPaddingLeft(10);
        packageCommentsHeader.setPaddingBottom(10);

        PdfPCell packageComments = new PdfPCell(new Paragraph(pack.getComments(), normalFont));
        packageComments.setBorder(Rectangle.NO_BORDER);
        packageComments.setColspan(2);
        packageComments.setPaddingLeft(10);
        packageComments.setPaddingRight(10);


        //konfiguracja tabeli nadawcy
        PdfPCell titleSenderHeader = new PdfPCell(new Paragraph("Dane nadawcy", boldFont16));
        titleSenderHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleSenderHeader.setBorder(Rectangle.NO_BORDER);
        titleSenderHeader.setPaddingBottom(25);
        titleSenderHeader.setColspan(2);

        PdfPCell senderNameHeader = new PdfPCell(new Paragraph("Imię i nazwisko:", boldFont));
        senderNameHeader.setBorder(Rectangle.NO_BORDER);
        senderNameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        senderNameHeader.setPaddingLeft(10);
        senderNameHeader.setPaddingBottom(10);

        PdfPCell senderName = new PdfPCell(new Paragraph(pack.getSender().getName() + " " + pack.getSender().getLastName()));
        senderName.setBorder(Rectangle.NO_BORDER);

        PdfPCell senderPhoneNumberHeader = new PdfPCell(new Paragraph("Numer telefonu:", boldFont));
        senderPhoneNumberHeader.setBorder(Rectangle.NO_BORDER);
        senderPhoneNumberHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        senderPhoneNumberHeader.setPaddingLeft(10);
        senderPhoneNumberHeader.setPaddingBottom(10);

        PdfPCell senderPhoneNumber = new PdfPCell(new Paragraph(pack.getSender().getPhoneNumber()));
        senderPhoneNumber.setBorder(Rectangle.NO_BORDER);

        PdfPCell senderAddressHeader = new PdfPCell(new Paragraph("Adres:", boldFont));
        senderAddressHeader.setPaddingLeft(10);
        senderAddressHeader.setBorder(Rectangle.NO_BORDER);

        PdfPCell senderAddress= new PdfPCell(new Paragraph(pack.getSender().getPostCode() + ", " + pack.getSender().getCity() + "\nul. " + pack.getSender().getStreet()));
        senderAddress.setPaddingRight(10);
        senderAddress.setBorder(Rectangle.NO_BORDER);

        //konfiguracja tabeli odbiorcy
        PdfPCell titleReceiverHeader = new PdfPCell(new Paragraph("Dane odbiorcy", boldFont16));
        titleReceiverHeader.setColspan(2);
        titleReceiverHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleReceiverHeader.setBorder(Rectangle.NO_BORDER);
        titleReceiverHeader.setPaddingBottom(25);

        PdfPCell receiverNameHeader = new PdfPCell(new Paragraph("Imię i nazwisko:", boldFont));
        receiverNameHeader.setBorder(Rectangle.NO_BORDER);
        receiverNameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        receiverNameHeader.setPaddingLeft(10);
        receiverNameHeader.setPaddingBottom(10);

        PdfPCell receiverName = new PdfPCell(new Paragraph(pack.getRecipient().getName() + " " + pack.getRecipient().getLastName()));
        receiverName.setBorder(Rectangle.NO_BORDER);

        PdfPCell receiverPhoneNumberHeader = new PdfPCell(new Paragraph("Numer telefonu:", boldFont));
        receiverPhoneNumberHeader.setBorder(Rectangle.NO_BORDER);
        receiverPhoneNumberHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        receiverPhoneNumberHeader.setPaddingLeft(10);
        receiverPhoneNumberHeader.setPaddingBottom(10);

        PdfPCell receiverPhoneNumber = new PdfPCell(new Paragraph(pack.getRecipient().getPhoneNumber()));
        receiverPhoneNumber.setBorder(Rectangle.NO_BORDER);

        PdfPCell receiverAddressHeader = new PdfPCell(new Paragraph("Adres:", boldFont));
        receiverAddressHeader.setPaddingLeft(10);
        receiverAddressHeader.setBorder(Rectangle.NO_BORDER);

        PdfPCell receiverAddress= new PdfPCell(new Paragraph(pack.getRecipient().getPostCode() + ", " + pack.getRecipient().getCity() + "\nul. " + pack.getRecipient().getStreet()));
        receiverAddress.setPaddingRight(10);
        receiverAddress.setBorder(Rectangle.NO_BORDER);

        //stworzenie obrazka QR kod
        PdfPCell cell3 = new PdfPCell();

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(pack.getCode().toString(), BarcodeFormat.QR_CODE, 256, 256);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        Image image = Image.getInstance(pngData);
        cell3.setImage(image); //przypisanie obrazka do komórki tabeli

        //dodanie danych do tabeli z Paczka
        packageTable.addCell(titleHeader);
        packageTable.addCell(packageNumberHeader);
        packageTable.addCell(packageNumber);
        packageTable.addCell(packageSizeHeader);
        packageTable.addCell(packageSize);
        packageTable.addCell(packageCommentsHeader);
        packageTable.addCell(packageComments);

        //dodanie danych do tabeli z nadawca
        senderTable.addCell(titleSenderHeader);
        senderTable.addCell(senderNameHeader);
        senderTable.addCell(senderName);
        senderTable.addCell(senderPhoneNumberHeader);
        senderTable.addCell(senderPhoneNumber);
        senderTable.addCell(senderAddressHeader);
        senderTable.addCell(senderAddress);

        //dodanie danych do tabeli z odbiorca
        recipientTable.addCell(titleReceiverHeader);
        recipientTable.addCell(receiverNameHeader);
        recipientTable.addCell(receiverName);
        recipientTable.addCell(receiverPhoneNumberHeader);
        recipientTable.addCell(receiverPhoneNumber);
        recipientTable.addCell(receiverAddressHeader);
        recipientTable.addCell(receiverAddress);

        //dodanie tabeli oraz kodu QR do wiekszej tabeli
        AllTable.addCell(packageTable);
        AllTable.addCell(cell3);
        AllTable.addCell(senderTable);
        AllTable.addCell(recipientTable);

        document.add(AllTable);


        document.close(); //zamkniecie dokumentu
        writer.close();
    }
}
