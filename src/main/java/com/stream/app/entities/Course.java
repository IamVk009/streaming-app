package com.stream.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course_videos")
public class Course {

    @Id
    private String courseId;
    private String title;

//    @OneToMany(mappedBy = "course")
//    private List<Video> courseList = new ArrayList<>();
}
