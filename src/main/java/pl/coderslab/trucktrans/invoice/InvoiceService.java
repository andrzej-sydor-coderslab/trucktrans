package pl.coderslab.trucktrans.invoice;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.RichTextString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.trucktrans.model.Invoice;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public boolean createPdf(List<Invoice> invoices, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            String filePath = servletContext.getRealPath("/src/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists) {
                new File(filePath).mkdir();
            }
//            boolean exists = file.exists();
//            if (!exists) {
//                boolean dirCreated = file.mkdir();
//            }


            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "invoices" + ".pdf"));
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/invoices.pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Invoice list", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell invoiceNumber = new PdfPCell(new Paragraph("Invoice number", tableHeader));
            invoiceNumber.setBorderColor(BaseColor.BLACK);
            invoiceNumber.setPaddingLeft(10);
            invoiceNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
            invoiceNumber.setVerticalAlignment(Element.ALIGN_CENTER);
            invoiceNumber.setBackgroundColor(BaseColor.DARK_GRAY);
            invoiceNumber.setExtraParagraphSpace(5);
            table.addCell(invoiceNumber);

            PdfPCell invoiceDate = new PdfPCell(new Paragraph("Invoice date", tableHeader));
            invoiceDate.setBorderColor(BaseColor.BLACK);
            invoiceDate.setPaddingLeft(10);
            invoiceDate.setHorizontalAlignment(Element.ALIGN_CENTER);
            invoiceDate.setVerticalAlignment(Element.ALIGN_CENTER);
            invoiceDate.setBackgroundColor(BaseColor.DARK_GRAY);
            invoiceDate.setExtraParagraphSpace(5);
            table.addCell(invoiceDate);

            PdfPCell paymentDate = new PdfPCell(new Paragraph("Payment date", tableHeader));
            paymentDate.setBorderColor(BaseColor.BLACK);
            paymentDate.setPaddingLeft(10);
            paymentDate.setHorizontalAlignment(Element.ALIGN_CENTER);
            paymentDate.setVerticalAlignment(Element.ALIGN_CENTER);
            paymentDate.setBackgroundColor(BaseColor.DARK_GRAY);
            paymentDate.setExtraParagraphSpace(5);
            table.addCell(paymentDate);

            PdfPCell contractor = new PdfPCell(new Paragraph("Contractor", tableHeader));
            contractor.setBorderColor(BaseColor.BLACK);
            contractor.setPaddingLeft(10);
            contractor.setHorizontalAlignment(Element.ALIGN_CENTER);
            contractor.setVerticalAlignment(Element.ALIGN_CENTER);
            contractor.setBackgroundColor(BaseColor.DARK_GRAY);
            contractor.setExtraParagraphSpace(5);
            table.addCell(contractor);

            PdfPCell netAmount = new PdfPCell(new Paragraph("Net amount", tableHeader));
            netAmount.setBorderColor(BaseColor.BLACK);
            netAmount.setPaddingLeft(10);
            netAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
            netAmount.setVerticalAlignment(Element.ALIGN_CENTER);
            netAmount.setBackgroundColor(BaseColor.DARK_GRAY);
            netAmount.setExtraParagraphSpace(5);
            table.addCell(netAmount);

            PdfPCell vatRate = new PdfPCell(new Paragraph("Vat rate %", tableHeader));
            vatRate.setBorderColor(BaseColor.BLACK);
            vatRate.setPaddingLeft(10);
            vatRate.setHorizontalAlignment(Element.ALIGN_CENTER);
            vatRate.setVerticalAlignment(Element.ALIGN_CENTER);
            vatRate.setBackgroundColor(BaseColor.DARK_GRAY);
            vatRate.setExtraParagraphSpace(5);
            table.addCell(vatRate);

            PdfPCell vatAmount = new PdfPCell(new Paragraph("Vat amount", tableHeader));
            vatAmount.setBorderColor(BaseColor.BLACK);
            vatAmount.setPaddingLeft(10);
            vatAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
            vatAmount.setVerticalAlignment(Element.ALIGN_CENTER);
            vatAmount.setBackgroundColor(BaseColor.DARK_GRAY);
            vatAmount.setExtraParagraphSpace(5);
            table.addCell(vatAmount);

            PdfPCell grossAmount = new PdfPCell(new Paragraph("Gross amount", tableHeader));
            grossAmount.setBorderColor(BaseColor.BLACK);
            grossAmount.setPaddingLeft(10);
            grossAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
            grossAmount.setVerticalAlignment(Element.ALIGN_CENTER);
            grossAmount.setBackgroundColor(BaseColor.DARK_GRAY);
            grossAmount.setExtraParagraphSpace(5);
            table.addCell(grossAmount);

            PdfPCell status = new PdfPCell(new Paragraph("Status", tableHeader));
            status.setBorderColor(BaseColor.BLACK);
            status.setPaddingLeft(10);
            status.setHorizontalAlignment(Element.ALIGN_CENTER);
            status.setVerticalAlignment(Element.ALIGN_CENTER);
            status.setBackgroundColor(BaseColor.DARK_GRAY);
            status.setExtraParagraphSpace(5);
            table.addCell(status);

            for (Invoice invoiceDoc : invoices) {
                PdfPCell invoiceNumberValue = new PdfPCell(new Paragraph(invoiceDoc.getInvoiceNumber(), tableBody));
                invoiceNumberValue.setBorderColor(BaseColor.BLACK);
                invoiceNumberValue.setPaddingLeft(10);
                invoiceNumberValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                invoiceNumberValue.setVerticalAlignment(Element.ALIGN_CENTER);
                invoiceNumberValue.setBackgroundColor(BaseColor.WHITE);
                invoiceNumberValue.setExtraParagraphSpace(5);
                table.addCell(invoiceNumberValue);

                PdfPCell invoiceDateValue = new PdfPCell(new Paragraph(String.valueOf(invoiceDoc.getInvoiceDate()), tableBody));
                invoiceDateValue.setBorderColor(BaseColor.BLACK);
                invoiceDateValue.setPaddingLeft(10);
                invoiceDateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                invoiceDateValue.setVerticalAlignment(Element.ALIGN_CENTER);
                invoiceDateValue.setBackgroundColor(BaseColor.WHITE);
                invoiceDateValue.setExtraParagraphSpace(5);
                table.addCell(invoiceDateValue);

                PdfPCell paymentDateValue = new PdfPCell(new Paragraph(String.valueOf(invoiceDoc.getPaymentDate()), tableBody));
                paymentDateValue.setBorderColor(BaseColor.BLACK);
                paymentDateValue.setPaddingLeft(10);
                paymentDateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                paymentDateValue.setVerticalAlignment(Element.ALIGN_CENTER);
                paymentDateValue.setBackgroundColor(BaseColor.WHITE);
                paymentDateValue.setExtraParagraphSpace(5);
                table.addCell(paymentDateValue);

                PdfPCell contractorValue = new PdfPCell(new Paragraph(String.valueOf(invoiceDoc.getContractor()), tableBody));
                contractorValue.setBorderColor(BaseColor.BLACK);
                contractorValue.setPaddingLeft(10);
                contractorValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                contractorValue.setVerticalAlignment(Element.ALIGN_CENTER);
                contractorValue.setBackgroundColor(BaseColor.DARK_GRAY);
                contractorValue.setExtraParagraphSpace(5);
                table.addCell(contractorValue);

                PdfPCell netAmountValue = new PdfPCell(new Paragraph(String.valueOf(invoiceDoc.getTotalNetAmount()), tableBody));
                netAmountValue.setBorderColor(BaseColor.BLACK);
                netAmountValue.setPaddingLeft(10);
                netAmountValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                netAmountValue.setVerticalAlignment(Element.ALIGN_CENTER);
                netAmountValue.setBackgroundColor(BaseColor.WHITE);
                netAmountValue.setExtraParagraphSpace(5);
                table.addCell(netAmountValue);

                PdfPCell vatRateValue = new PdfPCell(new Paragraph(String.valueOf(invoiceDoc.getVatRateInPercent()), tableBody));
                vatRateValue.setBorderColor(BaseColor.BLACK);
                vatRateValue.setPaddingLeft(10);
                vatRateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                vatRateValue.setVerticalAlignment(Element.ALIGN_CENTER);
                vatRateValue.setBackgroundColor(BaseColor.WHITE);
                vatRateValue.setExtraParagraphSpace(5);
                table.addCell(vatRateValue);

                PdfPCell vatAmountValue = new PdfPCell(new Paragraph("Vat amount", tableBody));
                vatAmount.setBorderColor(BaseColor.BLACK);
                vatAmount.setPaddingLeft(10);
                vatAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
                vatAmount.setVerticalAlignment(Element.ALIGN_CENTER);
                vatAmount.setBackgroundColor(BaseColor.DARK_GRAY);
                vatAmount.setExtraParagraphSpace(5);
                table.addCell(vatAmount);

                PdfPCell grossAmountValue = new PdfPCell(new Paragraph(String.valueOf(invoiceDoc.getTotalGrossAmount()), tableBody));
                grossAmountValue.setBorderColor(BaseColor.BLACK);
                grossAmountValue.setPaddingLeft(10);
                grossAmountValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                grossAmountValue.setVerticalAlignment(Element.ALIGN_CENTER);
                grossAmountValue.setBackgroundColor(BaseColor.WHITE);
                grossAmountValue.setExtraParagraphSpace(5);
                table.addCell(grossAmountValue);

                PdfPCell statusValue = new PdfPCell(new Paragraph(String.valueOf(invoiceDoc.getIsPaid()), tableBody));
                statusValue.setBorderColor(BaseColor.BLACK);
                statusValue.setPaddingLeft(10);
                statusValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                statusValue.setVerticalAlignment(Element.ALIGN_CENTER);
                statusValue.setBackgroundColor(BaseColor.WHITE);
                statusValue.setExtraParagraphSpace(5);
                table.addCell(statusValue);

            }

            document.add(table);
            document.close();
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public boolean createExcel(List<Invoice> invoices, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);

        String filePath = servletContext.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists) {
            new File(filePath).mkdirs();
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "invoices" + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("invoices");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
//            headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
//            headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell invoiceNumber = headerRow.createCell(0);
            invoiceNumber.setCellValue("Invoice number");
            invoiceNumber.setCellStyle(headerCellStyle);

            HSSFCell invoiceDate = headerRow.createCell(1);
            invoiceDate.setCellValue("Invoice date");
            invoiceDate.setCellStyle(headerCellStyle);

            HSSFCell paymentDate = headerRow.createCell(2);
            paymentDate.setCellValue("Payment date");
            paymentDate.setCellStyle(headerCellStyle);

            HSSFCell contractor = headerRow.createCell(3);
            contractor.setCellValue("Contractor");
            contractor.setCellStyle(headerCellStyle);

            HSSFCell netAmount = headerRow.createCell(4);
            netAmount.setCellValue("Net amount");
            netAmount.setCellStyle(headerCellStyle);

            HSSFCell vatRate = headerRow.createCell(5);
            vatRate.setCellValue("Vat rate %");
            vatRate.setCellStyle(headerCellStyle);

            HSSFCell vatAmount = headerRow.createCell(6);
            vatAmount.setCellValue("Vat amount");
            vatAmount.setCellStyle(headerCellStyle);

            HSSFCell grossAmount = headerRow.createCell(7);
            grossAmount.setCellValue("Gross amount");
            grossAmount.setCellStyle(headerCellStyle);

            HSSFCell status = headerRow.createCell(8);
            status.setCellValue("Status");
            status.setCellStyle(headerCellStyle);

            int i = 1;
            for (Invoice invoice : invoices) {
                HSSFRow bodyRow = workSheet.createRow(i);
                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
//                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell invoiceNumberValue = bodyRow.createCell(0);
                invoiceNumberValue.setCellValue(invoice.getInvoiceNumber());
                invoiceNumberValue.setCellStyle(bodyCellStyle);

                HSSFCell invoiceDateValue = bodyRow.createCell(1);
                invoiceDateValue.setCellValue(invoice.getPaymentDate());
                invoiceDateValue.setCellStyle(bodyCellStyle);

                HSSFCell paymentDateValue = bodyRow.createCell(2);
                paymentDateValue.setCellValue(invoice.getPaymentDate());
                paymentDateValue.setCellStyle(bodyCellStyle);

                HSSFCell contractorValue = bodyRow.createCell(3);
                contractorValue.setCellValue((RichTextString) invoice.getContractor());
                contractorValue.setCellStyle(bodyCellStyle);

                HSSFCell netAmountValue = bodyRow.createCell(4);
                netAmountValue.setCellValue(invoice.getTotalNetAmount());
                netAmountValue.setCellStyle(bodyCellStyle);

                HSSFCell vatRateValue = bodyRow.createCell(5);
                vatRateValue.setCellValue(invoice.getVatRateInPercent());
                vatRateValue.setCellStyle(bodyCellStyle);

                HSSFCell vatAmountValue = bodyRow.createCell(6);
                vatAmountValue.setCellValue(invoice.getTotalVatAmount());
                vatAmountValue.setCellStyle(bodyCellStyle);

                HSSFCell grossAmountValue = bodyRow.createCell(7);
                grossAmountValue.setCellValue(invoice.getTotalGrossAmount());
                grossAmountValue.setCellStyle(bodyCellStyle);

                HSSFCell statusValue = bodyRow.createCell(8);
                statusValue.setCellValue(invoice.getIsPaid());
                statusValue.setCellStyle(bodyCellStyle);

                i++;
            }

            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;

        } catch (
                Exception e) {
            return false;
        }
    }


}




