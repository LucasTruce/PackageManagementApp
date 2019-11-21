package pl.packagemanagement.model.warehouse;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.car.Car;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.code.CodeRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements  WarehouseService{
    private final WarehouseRepository warehouseRepository;
    private final CodeRepository codeRepository;

    @Override
    public Page<Warehouse> findAll(int pageNumber, int pageSize, String orderBy, String direction) {
        Page<Warehouse> pagedWarehouse;
        if(direction.equals(Sort.Direction.ASC.name()))
            pagedWarehouse = warehouseRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderBy)));
        else
            pagedWarehouse = warehouseRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, orderBy)));
        return pagedWarehouse;
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        Code code = this.codeRepository.save(warehouse.getCode());
        warehouse.setCode(code);
        warehouse = warehouseRepository.save(warehouse);
        warehouse.getCode().setWarehouse(warehouse);

        createFile(warehouse);

        return warehouse;
    }

    @Override
    public void delete(Warehouse warehouse) {
        warehouseRepository.delete(warehouse);
    }

    @Override
    public Warehouse update(Warehouse warehouse) {
        createFile(warehouse);

        return warehouseRepository.save(warehouse);
    }

    private void createFile(Warehouse warehouse){
        try {
            String fileName = warehouse.getCity() + "-" + warehouse.getStreet() +  ".pdf";
            String path = "src/main/resources/warehouses";
            String finalPath = path + "/" + fileName;
            createDocument(warehouse, path, finalPath); //tworzenie dokumentu pdf
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getQRBytes(Code code, int width, int height) throws Exception{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Code tempCode = codeRepository.findById(code.getId()).get();
        BitMatrix bitMatrix = qrCodeWriter.encode(tempCode.toString(), BarcodeFormat.QR_CODE,  width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    private void createDocument(Warehouse warehouse, String filePath, String finalPath) throws Exception {
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

        PdfPTable allTable = createWarehouseTable(warehouse, boldFont, boldFont16, normalFont);
        document.add(allTable);

        document.close(); //zamkniecie dokumentu
        writer.close();
    }

    private PdfPTable createWarehouseTable(Warehouse warehouse, Font boldFont, Font boldFont16, Font normalFont) throws Exception{
        PdfPTable allTable = new PdfPTable(2);
        allTable.setWidthPercentage(100);
        allTable.setSpacingBefore(10f);
        allTable.setSpacingAfter(25f);

        PdfPTable warehouseTable = new PdfPTable(2);
        warehouseTable.setWidthPercentage(40);
        warehouseTable.setSpacingBefore(10f);
        warehouseTable.setSpacingAfter(10f);
        warehouseTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        //ustawienia szerokosci kolumn dla danych paczki
        float[] columnWidths = {1.5f, 1.5f};
        warehouseTable.setWidths(columnWidths);

        PdfPTable codeTable = new PdfPTable(1);
        codeTable.setSpacingBefore(10f);
        //codeTable.setSpacingAfter(10f);
        codeTable.setHorizontalAlignment(Element.ALIGN_CENTER);


        //konfiguracja tabeli paczki
        PdfPCell titleHeader = new PdfPCell(new Paragraph("Dane magazynu", boldFont16));
        titleHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleHeader.setPaddingBottom(25);
        titleHeader.setBorder(Rectangle.NO_BORDER);
        titleHeader.setColspan(2);


        PdfPCell warehouseAddressHeader = new PdfPCell(new Paragraph("Adres:", boldFont));
        warehouseAddressHeader.setBorder(Rectangle.NO_BORDER);
        warehouseAddressHeader.setPaddingLeft(10);
        warehouseAddressHeader.setPaddingBottom(10);

        PdfPCell warehouseAddress = new PdfPCell(new Paragraph(warehouse.getPostCode() + ", " + warehouse.getCity() + "\nul. " + warehouse.getStreet()));
        warehouseAddress.setBorder(Rectangle.NO_BORDER); //brak obramowania

        PdfPCell warehousePhoneNumberHeader = new PdfPCell(new Paragraph("Numer telefonu:", boldFont));
        warehousePhoneNumberHeader.setBorder(Rectangle.NO_BORDER);
        warehousePhoneNumberHeader.setPaddingLeft(10);
        warehousePhoneNumberHeader.setPaddingBottom(10);

        PdfPCell warehousePhoneNumber = new PdfPCell(new Paragraph(warehouse.getPhoneNumber()));
        warehousePhoneNumber.setBorder(Rectangle.NO_BORDER);

        PdfPCell warehouseDescriptionHeader = new PdfPCell(new Paragraph("Opis:", boldFont));
        warehouseDescriptionHeader.setBorder(Rectangle.NO_BORDER);
        warehouseDescriptionHeader.setPaddingLeft(10);
        warehouseDescriptionHeader.setPaddingBottom(10);
        warehouseDescriptionHeader.setColspan(2);

        PdfPCell warehouseDescription = new PdfPCell(new Paragraph(warehouse.getDescription(), normalFont));
        warehouseDescription.setBorder(Rectangle.NO_BORDER);
        warehouseDescription.setColspan(2);
        warehouseDescription.setPaddingRight(10);
        warehouseDescription.setPaddingLeft(10);


        //konfiguracja tabeli z kodem
        PdfPCell codeTitle = new PdfPCell(new Paragraph("Kod QR magazynu", boldFont16));
        codeTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        codeTitle.setBorder(Rectangle.NO_BORDER);
        //codeTitle.setPaddingBottom(25);


        //stworzenie obrazka QR kod
        PdfPCell cell3 = new PdfPCell();
        cell3.setBorder(Rectangle.NO_BORDER);


        byte[] pngData = getQRBytes(warehouse.getCode(), 150, 150);
        Image image = Image.getInstance(pngData);
        cell3.setImage(image);//przypisanie obrazka do komórki tabeli

        //dodanie danych do tabeli z Paczka
        warehouseTable.addCell(titleHeader);
        warehouseTable.addCell(warehouseAddressHeader);
        warehouseTable.addCell(warehouseAddress);
        warehouseTable.addCell(warehousePhoneNumberHeader);
        warehouseTable.addCell(warehousePhoneNumber);
        warehouseTable.addCell(warehouseDescriptionHeader);
        warehouseTable.addCell(warehouseDescription);

        codeTable.addCell(codeTitle);
        codeTable.addCell(cell3);

        //dodanie tabeli oraz kodu QR do wiekszej tabeli
        allTable.addCell(warehouseTable);
        allTable.addCell(codeTable);

        return allTable;
    }
}
