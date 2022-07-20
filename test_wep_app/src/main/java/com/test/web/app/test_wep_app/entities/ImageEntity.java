package com.test.web.app.test_wep_app.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "image_size")
	private long size;

	@Column(name = "file_extension")
	private String fileExtension;

	@Column(name = "download_url")
	private String downloadUrl;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
