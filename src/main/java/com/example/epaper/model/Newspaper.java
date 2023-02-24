package com.example.epaper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Model for representing newspaper in database
 */
@Entity
@Table(name = "NEWSPAPER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Newspaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NEWSPAPER_NAME")
    private String newspaperName;
    @Column(name = "DEVICE_WIDTH")
    private Long deviceWidth;
    @Column(name = "DEVICE_HEIGHT")
    private Long deviceHeight;
    @Column(name = "DEVICE_DPI")
    private Long deviceDpi;
    @Column(name = "UPLOAD_TIME")
    private LocalDateTime uploadTime;
    @Column(name = "FILENAME")
    private String filename;

}
