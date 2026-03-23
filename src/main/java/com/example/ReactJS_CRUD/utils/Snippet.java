package com.example.ReactJS_CRUD.utils;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.ReactJS_CRUD.Entity.EmployeeMaster;
import com.example.ReactJS_CRUD.repository.DepartmentRepository;
import com.example.ReactJS_CRUD.repository.RoleRepository;

@Component
public class Snippet {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    public String generatePdf(EmployeeMaster employee) throws IOException 
    {

        String pdfPath = "C:/uploads/employees/employee_" + employee.getId() + ".pdf";

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream content = new PDPageContentStream(document, page);

        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 16);
        content.setLeading(20f);
        content.newLineAtOffset(50, 700);

        content.showText("Employee Details");
        content.newLine();

        content.setFont(PDType1Font.HELVETICA, 12);

        content.showText("Name: " + employee.getName());
        content.newLine();
        
        content.showText("Phone: " + employee.getPhoneNumber());
        content.newLine();
        
        content.showText("Password: " + employee.getPassword());
        content.newLine();

        content.showText("Department: " + employee.getDepartment().getDepartmentName());
        content.newLine();

        content.showText("Role: " + employee.getRole().getRoleName());
        content.newLine();

        content.showText("Created Date: " + employee.getCreatedDate());
        content.newLine();

        content.endText();

        // Add Image (if exists)
        if (employee.getImagePath() != null) {
            try {
                var image = org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
                        .createFromFile(employee.getImagePath(), document);

                content.drawImage(image, 50, 400, 150, 150);
            } catch (Exception e) {
                System.out.println("Image not found or error loading image");
            }
        }

        content.close();
        document.save(new File(pdfPath));
        document.close();

        return pdfPath;
    }
}