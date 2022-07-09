package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFilesTest {

    ClassLoader classLoader = ZipFilesTest.class.getClassLoader();

    @Test
    @DisplayName("Реализовать чтение и проверку содержимого каждого файла из архива")
    void zipTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("zip_example.zip")) {
            assert is != null;
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("pdf")) {
                    PDF pdf = new PDF(zis);
                    assertThat(pdf.text).contains("Работа с файлами");
                } else if (entry.getName().contains("xslx")) {
                    XLS xls = new XLS(zis);
                    assertThat(
                            xls.excel.getSheetAt(0)
                                    .getRow(0)
                                    .getCell(0)
                                    .getStringCellValue())
                                    .contains("XLSX_example");
                } else if (entry.getName().contains("csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis, UTF_8));
                    List<String[]> csv = csvReader.readAll();
                    assertThat(csv).contains(
                            new String[]{"name", "age"},
                            new String[]{"Elena", "32"},
                            new String[]{"Daria", "29"});
                }
            }
        }
    }
}
