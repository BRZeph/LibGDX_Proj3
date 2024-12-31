package me.BRZeph.AI.Core;

import java.io.IOException;

public class ExcelExporter {
    public static void main(String[] args) {
        try {
            String command = "java -DisSubProcess=true -jar C:/Users/Ricardo/Documents/GitHub/LibGDX_Proj3/out/artifacts/ExcelExport_jar/ExportExcelData.jar";

            Process process = new ProcessBuilder(command.split(" "))
                .redirectErrorStream(true)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
