package common;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GoogleDriveService {

    private static final String APPLICATION_NAME = "IoT Secure Storage";
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIR = "tokens";

    private Drive driveService;

    public GoogleDriveService() throws Exception {
        driveService = createDriveService();
    }

    private Drive createDriveService() throws Exception {

        var httpTransport =
                GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                new InputStreamReader(
                        Files.newInputStream(
                                java.nio.file.Paths.get("credentials.json")
                        )
                )
        );

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport,
                        JSON_FACTORY,
                        clientSecrets,
                        Collections.singleton(DriveScopes.DRIVE_FILE)
                )
                .setDataStoreFactory(
                        new FileDataStoreFactory(
                                new java.io.File(TOKENS_DIR)
                        )
                )
                .setAccessType("offline")
                .build();

        var credential =
                new com.google.api.client.extensions.java6.auth.oauth2
                        .AuthorizationCodeInstalledApp(
                        flow,
                        new com.google.api.client.extensions.jetty.auth.oauth2
                                .LocalServerReceiver()
                ).authorize("user");

        
        HttpRequestInitializer requestInitializer = request -> {
            credential.initialize(request);
            request.setConnectTimeout(3 * 60000);
            request.setReadTimeout(3 * 60000);
        };

        return new Drive.Builder(
                httpTransport,
                JSON_FACTORY,
                requestInitializer
        )
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    

    
    public String getOrCreateFolder(String folderName) throws Exception {

        String query =
                "mimeType='application/vnd.google-apps.folder' " +
                "and name='" + folderName + "' and trashed=false";

        FileList result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id, name)")
                .execute();

        if (!result.getFiles().isEmpty()) {
            return result.getFiles().get(0).getId();
        }

        File folderMetadata = new File();
        folderMetadata.setName(folderName);
        folderMetadata.setMimeType(
                "application/vnd.google-apps.folder"
        );

        File folder = driveService.files()
                .create(folderMetadata)
                .setFields("id")
                .execute();

        return folder.getId();
    }

    
    public String getOrCreateSubFolder(
            String parentFolderId,
            String folderName
    ) throws Exception {

        String query =
                "mimeType='application/vnd.google-apps.folder' " +
                "and name='" + folderName + "' " +
                "and '" + parentFolderId + "' in parents " +
                "and trashed=false";

        FileList result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id, name)")
                .execute();

        if (!result.getFiles().isEmpty()) {
            return result.getFiles().get(0).getId();
        }

        File folderMetadata = new File();
        folderMetadata.setName(folderName);
        folderMetadata.setMimeType(
                "application/vnd.google-apps.folder"
        );
        folderMetadata.setParents(
                Collections.singletonList(parentFolderId)
        );

        File folder = driveService.files()
                .create(folderMetadata)
                .setFields("id")
                .execute();

        return folder.getId();
    }

    

    public void uploadFileToFolder(
            String folderId,
            String fileName,
            String mimeType,
            java.io.File file
    ) throws Exception {

        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(
                Collections.singletonList(folderId)
        );

        FileContent mediaContent =
                new FileContent(mimeType, file);

        driveService.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
    }
    


public FileList listFilesInFolder(String folderId) throws Exception {

    String query = "'" + folderId + "' in parents and trashed=false";

    return driveService.files().list()
            .setQ(query)
            .setSpaces("drive")
            .setFields("files(id, name, size)")
            .execute();
}


public java.io.InputStream downloadFile(String fileId) throws Exception {
    return driveService.files()
            .get(fileId)
            .executeMediaAsInputStream();
}
}
