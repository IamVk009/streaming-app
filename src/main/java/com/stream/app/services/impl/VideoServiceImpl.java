package com.stream.app.services.impl;

import com.stream.app.entities.Video;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.services.VideoService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Value("${files.video}")
    private String folderPath;

//    @PostConstruct is used to indicate that a method should be executed after dependency injection is done to perform any initialization tasks.
//    This method runs after the constructor and after all dependencies are injected, but before the class is available for use.
    @PostConstruct
    public void init() {
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdir();
            log.info("Destination folder created");
        } else log.info("Destination folder already exists");
    }


    @Override
    public Video saveVideo(Video video, MultipartFile file) {

        try {
//        Get data from multipart file
            String filename = file.getOriginalFilename();
            log.info("FileName : {}", filename);
            String contentType = file.getContentType();
            log.info("ContentType : {}", contentType);
//            In case we want to read data from file
            InputStream inputStream = file.getInputStream();

//            Cleaning filename and folderpath
            String cleanFolderPath = StringUtils.cleanPath(folderPath);
            String cleanFileName = null;
            if (filename != null) {
                cleanFileName = StringUtils.cleanPath(filename);
                log.info("CleanFileName : " + cleanFileName);
            }

//        Create folder path where video should get saved : destination
//        Specify folder path with filename
            Path destination = Paths.get(cleanFolderPath, cleanFileName);
            log.info("Destination : {}", destination);

//        Copy video file to the destination folder
//            Files.write(destination, file.getBytes());
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

//        Create file metadata
            video.setContentType(contentType);
            video.setFilePath(destination.toString());

//        Save the metadata to DB
            return videoRepository.save(video);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Video getVideoById(String id) {
        return null;
    }

    @Override
    public Video getVideoByTitle(String title) {
        return null;
    }

    @Override
    public List<Video> getAllVideos() {
        return List.of();
    }
}
