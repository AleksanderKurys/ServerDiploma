package net.javaservice.diplomaservice.event.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.entity.Event;
import net.javaservice.diplomaservice.event.entity.Image;
import net.javaservice.diplomaservice.event.repository.EventRepository;
import net.javaservice.diplomaservice.event.repository.ImageRepository;
import net.javaservice.diplomaservice.event.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final EventRepository eventRepository;

    public String uploadImage(MultipartFile file, Integer eventId) throws IOException {
        if(eventId != null) {
            var event = eventRepository.findById(eventId).orElseThrow();

            Image image = imageRepository.save(Image.builder()
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .event(event)
                .build());

            if (image!= null) {
                return "File uploaded";
            }
        }

        Image image = imageRepository.save(Image.builder()
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

        if (image!= null) {
            return "File uploaded";
        }

        return null;
    }
    public ArrayList<byte[]> downloadImage(Integer eventId) {
        ArrayList<Image> imageData = imageRepository.findByEventId(eventId);
        ArrayList<byte[]> images = new ArrayList<>();

        for(int i=0; i<imageData.size();i++) {
            byte[] image = ImageUtils.decompressImage(imageData.get(i).getImageData());
            images.add(image);
        }

        return images;
    }
}