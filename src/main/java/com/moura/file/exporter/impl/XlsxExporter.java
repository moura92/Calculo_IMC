package com.moura.file.exporter.impl;

import com.moura.dto.UsuarioDTO;
import com.moura.file.exporter.contract.FileExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class XlsxExporter implements FileExporter {
    @Override
    public Resource exportFile(List<UsuarioDTO> usuarios) throws Exception {
        try(Workbook workbook = new XSSFWorkbook()){                //Cria um arquivo Excel em memória
            Sheet sheet = workbook.createSheet("Usuarios");      //Cria uma aba chamada "Usuarios"

            Row headerRow = sheet.createRow(0);                                            //Cria a primeira linha (linha 0)
            String[] headers = {"ID", "nome", "altura", "peso", "imc", "classificacao_imc"}; //Define os nomes das colunas
            for (int i = 0; i < headers.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createheaderCellStyle(workbook));
            }

            //Preenchendo os dados:
            int rowIndex = 1;
            for (UsuarioDTO usuario : usuarios){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(usuario.getId());
                row.createCell(1).setCellValue(usuario.getNome());
                row.createCell(2).setCellValue(usuario.getAltura());
                row.createCell(3).setCellValue(usuario.getPeso());
                row.createCell(4).setCellValue(usuario.getImc().doubleValue());
                row.createCell(5).setCellValue(usuario.getClassificacao());
            }
            for (int i = 0; i < headers.length; i++) { //Faz o Excel ajustar automaticamente a largura das colunas
                sheet.autoSizeColumn(i);

            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    //Formata uma celula no excel
    private CellStyle createheaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
