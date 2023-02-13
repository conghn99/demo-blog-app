package com.example.demoblogapp.service;

import com.example.demoblogapp.entity.Blog;
import com.example.demoblogapp.entity.Image;
import com.example.demoblogapp.entity.User;
import com.example.demoblogapp.exception.BadRequestException;
import com.example.demoblogapp.exception.NotFoundException;
import com.example.demoblogapp.repository.ImageRepository;
import com.example.demoblogapp.repository.UserRepository;
import com.example.demoblogapp.response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public List<String> getAllImage() {
        // TODO: sau nay user chinh la user dang dang nhap
        Integer userId = 1;

        List<Image> images = imageRepository.findByUser_IdOrderByCreated_atDesc(userId);
        return images.stream()
                .map(image -> "/api/images/" + image.getId())
                .toList();
    }

    public byte[] readImage(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found image with id = " + id);
        });

        return image.getData();
    }

    public ImageResponse uploadImage(MultipartFile file) {
        // TODO: sau nay user chinh la user dang dang nhap
        Integer userId = 3;

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + userId);
        });

        // validate file
        validateFile(file);

        try {
            Image image = Image.builder()
                    .data(file.getBytes())
                    .user(user)
                    .build();
            imageRepository.save(image);
            String url = "/api/images/" + image.getId();
            return new ImageResponse(url);
        } catch (Exception e) {
            throw new RuntimeException("Upload Image Error");
        }
    }

    public void deleteImage(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found image with id = " + id);
        });

        imageRepository.delete(image);
    }

    private void validateFile(MultipartFile file) {
        // Kiểm tra tên file
        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.isEmpty()) {
            throw new BadRequestException("File ko được để trống");
        }

        // Kiểm tra đuôi file
        String fileExtension = getFileExtension(fileName);
        if(!checkFileExtension(fileExtension)) {
            throw new BadRequestException("File ko đúng định dạng");
        }

        // Kiểm tra dung lượng file (<= 2MB)
        double fileSize = (double) (file.getSize() / 1048576);
        if (fileSize > 2) {
            throw new BadRequestException("File ko được vượt quá 2MB");
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOf + 1);
    }

    private boolean checkFileExtension(String fileExtension) {
        List<String> extensions = new ArrayList<>(List.of("png", "jpg", "jpeg"));
        return extensions.contains(fileExtension.toLowerCase(Locale.ROOT));
    }
}
