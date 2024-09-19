package com.stream.app.services;

import com.stream.app.entities.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

//    save Video
    Video saveVido(Video video, MultipartFile multipartFile);
//    get Video By Id
    Video getVideoById(String id);
//    get Video By Title
    Video getVideoByTitle(String title);
//    get all videos
    List<Video> getAllVideos();
}
