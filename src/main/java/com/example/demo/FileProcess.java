package com.example.demo;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

public class FileProcess {
	private static final String ACCESS_TOKEN = "LstUJ6KoVFAAAAAAAAAAMj_E6EsR7ke59yIqb5AWvq3VjVADnIM46-IwTL44cGKv";
    public static String uploadToDropBox(MultipartFile file){
        String path = "";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        try  {
            FileMetadata metadata = client.files().uploadBuilder("/"+file.getOriginalFilename())
                    .uploadAndFinish(file.getInputStream());
            SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings("/"+file.getOriginalFilename());
            path = sharedLinkMetadata.getUrl();
        } catch (UploadErrorException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        path = (path.split("/?dl"))[0];
        path = path+"raw=1";
        return path;
    }
}
