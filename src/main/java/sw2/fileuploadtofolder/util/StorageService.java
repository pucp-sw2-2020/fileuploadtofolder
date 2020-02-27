package sw2.fileuploadtofolder.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

@Component
public class StorageService {

    String fileLocation = "C:/Users/Stuardo/Pictures/fileUploaded/";

    public HashMap<String, String> store(MultipartFile file) {

        HashMap<String, String> map = new HashMap<>();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                map.put("estado", "error");
                map.put("msg", "No se puede guardar un archivo vacío: " + filename);
            } else if (filename.contains("..")) {
                // Verificación contra ataques LFI
                map.put("estado", "error");
                map.put("msg", "No se permiten '..' en el archivo");
            } else {
                try (InputStream inputStream = file.getInputStream()) {
                    Path filePath = Paths.get(fileLocation);
                    Files.copy(inputStream,
                            filePath.resolve(filename),
                            StandardCopyOption.REPLACE_EXISTING);
                    map.put("estado", "exito");
                    map.put("fileName", filename);
                }
            }
        } catch (IOException e) {
            map.put("estado", "error");
            map.put("msg", "Error al guardar el archivo " + filename);
        }

        return map;
    }
}


