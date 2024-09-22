package com.stream.app.controller;

import com.stream.app.entities.Video;
import com.stream.app.payload.CustomMessage;
import com.stream.app.services.impl.VideoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private VideoServiceImpl videoService;

    public VideoController(VideoServiceImpl videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVideo(@RequestParam MultipartFile file,
                                         @RequestParam String title,
                                         @RequestParam String description) {

        Video video = new Video();
        video.setVideoId(UUID.randomUUID().toString());
        video.setTitle(title);
        video.setDescription(description);

        Video savedVideo = videoService.saveVideo(video, file);

        if (savedVideo != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedVideo);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomMessage
                            .builder()
                            .success(false)
                            .message("Video Not Uploaded...")
                            .build());
        }
    }
}
