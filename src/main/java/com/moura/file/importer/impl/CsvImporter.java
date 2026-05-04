package com.moura.file.importer.impl;

import com.moura.dto.UsuarioDTO;
import com.moura.file.importer.contact.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {
    @Override
    public List<UsuarioDTO> importFile(InputStream inputStream) throws Exception {

        CSVFormat format = CSVFormat.Builder.create()
                .setDelimiter(';')
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));

        return parseRecordsToUsuarioDTO(records);
    }

    private List<UsuarioDTO> parseRecordsToUsuarioDTO(Iterable<CSVRecord> records) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for (CSVRecord record : records){
            UsuarioDTO usuarioDTO = new UsuarioDTO();

            usuarioDTO.setNome(record.get("nome"));
            usuarioDTO.setIdade(Integer.parseInt(record.get("idade")));
            usuarioDTO.setAltura(Double.parseDouble(record.get("altura")));
            usuarioDTO.setPeso(Double.parseDouble(record.get("peso")));
            usuarioDTO.setImc(new BigDecimal(record.get("imc")));
            usuarioDTO.setClassificacao(record.get("classificacao"));
            usuarioDTO.setEnabled(true);
            usuarios.add(usuarioDTO);
        }

        return usuarios;
    }
}
