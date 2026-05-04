package com.moura.file.exporter.impl;

import com.moura.dto.UsuarioDTO;
import com.moura.file.exporter.contract.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements FileExporter {
    @Override
    public Resource exportFile(List<UsuarioDTO> usuarios) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "nome", "altura", "peso", "imc", "classificacao_imc")
                .setSkipHeaderRecord(false)
                .build();

        try(CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                "ID", "nome", "altura", "peso", "imc", "classificacao_imc"
        ))){

            for(UsuarioDTO usuario : usuarios){
                csvPrinter.printRecord(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getAltura(),
                        usuario.getPeso(),
                        usuario.getImc(),
                        usuario.getClassificacao()
                );
            }
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }
}
