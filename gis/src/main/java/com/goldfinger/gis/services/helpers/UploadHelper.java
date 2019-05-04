package com.goldfinger.gis.services.helpers;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class UploadHelper {
    private static final String EMPTY_DIRECTORY = "Provided directory is empty";
    private static final String SHP_NOT_FOUND = "SHP file not found";
    private static final String NOT_ZIP_FILE = "Provided file isn't .zip";

    private static final String MYSQL_WORKBENCH_PATH = "D:/Program Files/MySQL Workbench";
    private static final String directory = "D:/TEST/";

    private static int dirName = 1;
    private static int imgName = 1;

    public byte[] parseBase64(String base64String) {
        base64String = base64String.substring(base64String.indexOf(','));
        return Base64.decodeBase64(base64String);
    }

    public String createNewDirectory() {
        File file = new File(directory + dirName);
        while (file.isDirectory()) {
            dirName++;
            file = new File(directory + dirName);
        }
        file.mkdir();
        return directory + dirName + "/";
    }

    public String getZipSaveName() {
        return imgName + ".rar";
    }

    public String saveZip(byte[] fileBytes, String dirToSave) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dirToSave);
        fileOutputStream.write(fileBytes);
        fileOutputStream.close();
        return dirToSave;
    }

    public void unzip(String filePath, String dirToExtract) {
        try {
            ZipFile zipFile = new ZipFile(filePath);
            if (zipFile.isEncrypted()) {
                return;
            }
            zipFile.extractAll(dirToExtract);
        } catch (ZipException e) {
            throw new IllegalArgumentException(NOT_ZIP_FILE);
        }
    }

    public String getShpFileName(String dirOfShpFile) {
        File folder = new File(directory + dirName);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            throw new IllegalArgumentException(EMPTY_DIRECTORY);
        }
        String SHP = null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                if (listOfFile.getName().endsWith(".shp")) SHP = listOfFile.getName();
            }
        }
        if (SHP == null) {
            throw new IllegalArgumentException(SHP_NOT_FOUND);
        }

        SHP = SHP.replace(".shp", "");
        return SHP;
    }

    public void uploadShp(String shpFilePath, String tableToFetch) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd /d " + MYSQL_WORKBENCH_PATH + " && ogr2ogr -f MySQL MySQL:goldfingergis,host=localhost,port=3306,user=root,password=admin " + shpFilePath + ".shp -nln " + tableToFetch + " -update -overwrite -lco engine=MYISAM");
        builder.redirectErrorStream(true);
        builder.start();
    }
}
