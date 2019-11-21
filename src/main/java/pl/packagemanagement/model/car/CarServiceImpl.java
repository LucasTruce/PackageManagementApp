package pl.packagemanagement.model.car;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.carstatus.CarStatus;
import pl.packagemanagement.model.carstatus.CarStatusRepository;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.code.CodeRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarStatusRepository carStatusRepository;
    private final CodeRepository codeRepository;

    @Override
    public Page<Car> findAll(int pageNumber, int pageSize, String orderBy, String direction) {
        Page<Car> pagedCar;
        if(direction.equals(Sort.Direction.ASC.name()))
            pagedCar = carRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderBy)));
        else
            pagedCar = carRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, orderBy)));
        return pagedCar;
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car save(Car car) {
        CarStatus carStatus = carStatusRepository.findById(2l).get();
        carStatus.getCars().add(car);
        car.setCarStatus(carStatus);

        Code code = codeRepository.save(car.getCode());
        car.setCode(code);
        car = carRepository.save(car);
        car.getCode().setCar(car);

        createFile(car);

        return car;
    }

    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }

    @Override
    public Car update(Car car) {
        createFile(car);
        if(!car.getCode().getFilePath().equals(car.getLicensePlate()))
            car.getCode().setFilePath(car.getLicensePlate());

        return carRepository.save(car);
    }

    private void createFile(Car car){
        try {
            String fileName = car.getBrand() + "-" + car.getLicensePlate() +  ".pdf";
            String path = "src/main/resources/cars";
            String finalPath = path + "/" + fileName;
            createDocument(car, path, finalPath); //tworzenie dokumentu pdf
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

    private void createDocument(Car car, String filePath, String finalPath) throws Exception {
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

        PdfPTable allTable = createCarTable(car, boldFont, boldFont16, normalFont);
        document.add(allTable);

        document.close(); //zamkniecie dokumentu
        writer.close();
    }

    private PdfPTable createCarTable(Car car, Font boldFont, Font boldFont16, Font normalFont) throws Exception{
        PdfPTable allTable = new PdfPTable(2);
        allTable.setWidthPercentage(100);
        allTable.setSpacingBefore(10f);
        allTable.setSpacingAfter(25f);

        PdfPTable carTable = new PdfPTable(2);
        carTable.setWidthPercentage(40);
        carTable.setSpacingBefore(10f);
        carTable.setSpacingAfter(10f);
        carTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        //ustawienia szerokosci kolumn dla danych paczki
        float[] columnWidths = {1.5f, 1.5f};
        carTable.setWidths(columnWidths);

        PdfPTable codeTable = new PdfPTable(1);
        codeTable.setSpacingBefore(10f);
        //codeTable.setSpacingAfter(10f);
        codeTable.setHorizontalAlignment(Element.ALIGN_CENTER);


        //konfiguracja tabeli paczki
        PdfPCell titleHeader = new PdfPCell(new Paragraph("Dane samochodu", boldFont16));
        titleHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleHeader.setPaddingBottom(25);
        titleHeader.setBorder(Rectangle.NO_BORDER);
        titleHeader.setColspan(2);


        PdfPCell carBrandHeader = new PdfPCell(new Paragraph("Marka:", boldFont));
        carBrandHeader.setBorder(Rectangle.NO_BORDER);
        carBrandHeader.setPaddingLeft(10);
        carBrandHeader.setPaddingBottom(10);

        PdfPCell carBrand = new PdfPCell(new Paragraph(car.getBrand()));
        carBrand.setBorder(Rectangle.NO_BORDER); //brak obramowania

        PdfPCell carModelHeader = new PdfPCell(new Paragraph("Model:", boldFont));
        carModelHeader.setBorder(Rectangle.NO_BORDER);
        carModelHeader.setPaddingLeft(10);
        carModelHeader.setPaddingBottom(10);

        PdfPCell carModel = new PdfPCell(new Paragraph(car.getModel()));
        carModel.setBorder(Rectangle.NO_BORDER);

        PdfPCell carEngineTypeHeader = new PdfPCell(new Paragraph("Typ silnika:", boldFont));
        carEngineTypeHeader.setBorder(Rectangle.NO_BORDER);
        carEngineTypeHeader.setPaddingLeft(10);
        carEngineTypeHeader.setPaddingBottom(10);

        PdfPCell carEngineType = new PdfPCell(new Paragraph(car.getEngineType(), normalFont));
        carEngineType.setBorder(Rectangle.NO_BORDER);

        PdfPCell carLicensePlateHeader = new PdfPCell(new Paragraph("Numer rejestracyjny:", boldFont));
        carLicensePlateHeader.setBorder(Rectangle.NO_BORDER);
        carLicensePlateHeader.setPaddingLeft(10);
        carLicensePlateHeader.setPaddingBottom(10);

        PdfPCell carLicensePlate = new PdfPCell(new Paragraph(car.getLicensePlate(), normalFont));
        carLicensePlate.setBorder(Rectangle.NO_BORDER);

        PdfPCell carLoadHeader = new PdfPCell(new Paragraph("Ładowność[kg]:", boldFont));
        carLoadHeader.setBorder(Rectangle.NO_BORDER);
        carLoadHeader.setPaddingLeft(10);
        carLoadHeader.setPaddingBottom(10);

        PdfPCell carLoad = new PdfPCell(new Paragraph(car.getLoad() + "", normalFont));
        carLoad.setBorder(Rectangle.NO_BORDER);

        //konfiguracja tabeli z kodem
        PdfPCell codeTitle = new PdfPCell(new Paragraph("Kod QR samochodu", boldFont16));
        codeTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        codeTitle.setBorder(Rectangle.NO_BORDER);
        //codeTitle.setPaddingBottom(25);


        //stworzenie obrazka QR kod
        PdfPCell cell3 = new PdfPCell();
        cell3.setBorder(Rectangle.NO_BORDER);


        byte[] pngData = getQRBytes(car.getCode(), 150, 150);
        Image image = Image.getInstance(pngData);
        cell3.setImage(image);//przypisanie obrazka do komórki tabeli

        //dodanie danych do tabeli z Paczka
        carTable.addCell(titleHeader);
        carTable.addCell(carBrandHeader);
        carTable.addCell(carBrand);
        carTable.addCell(carModelHeader);
        carTable.addCell(carModel);
        carTable.addCell(carEngineTypeHeader);
        carTable.addCell(carEngineType);
        carTable.addCell(carLicensePlateHeader);
        carTable.addCell(carLicensePlate);
        carTable.addCell(carLoadHeader);
        carTable.addCell(carLoad);

        codeTable.addCell(codeTitle);
        codeTable.addCell(cell3);


        //dodanie tabeli oraz kodu QR do wiekszej tabeli
        allTable.addCell(carTable);
        allTable.addCell(codeTable);

        return allTable;
    }


}
