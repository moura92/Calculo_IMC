package com.moura.file.importer.impl;

import com.moura.dto.UsuarioDTO;
import com.moura.file.importer.contact.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {

    private final DataFormatter dataFormatter = new DataFormatter();

    @Override
    public List<UsuarioDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workBook = new XSSFWorkbook(inputStream)){
            XSSFSheet sheet = workBook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next(); // pula o cabeçalho

            return parseRowsToUsuarioDTOList(rowIterator);
        }
    }

    private List<UsuarioDTO> parseRowsToUsuarioDTOList(Iterator<Row> rowIterator) {
        List<UsuarioDTO> usuarios = new ArrayList<>();

        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            if (isRowValid(row)){
                usuarios.add(parseRowsToUsuarioDTO(row));
            }
        }
        return usuarios;
    }

    private UsuarioDTO parseRowsToUsuarioDTO(Row row) {

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setNome(dataFormatter.formatCellValue(row.getCell(0)));
        usuarioDTO.setIdade(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(1))));
        usuarioDTO.setAltura(Double.parseDouble(dataFormatter.formatCellValue(row.getCell(2)).replace(",", ".").trim()));
        usuarioDTO.setPeso(Double.parseDouble(dataFormatter.formatCellValue(row.getCell(3)).replace(",", ".").trim()));
        usuarioDTO.setImc(new BigDecimal(dataFormatter.formatCellValue(row.getCell(4)).replace(",", ".").trim()));
        usuarioDTO.setClassificacao(dataFormatter.formatCellValue(row.getCell(5)));
        usuarioDTO.setEnabled(true);
        return usuarioDTO;
    }

    private static boolean isRowValid(Row row) {
        return row != null && row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}


