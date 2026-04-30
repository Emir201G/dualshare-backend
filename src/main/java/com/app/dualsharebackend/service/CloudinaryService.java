package com.app.dualsharebackend.service;

import com.app.dualsharebackend.exception.CloudinaryNotFoundException;
import com.app.dualsharebackend.exception.CloudinaryServiceException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map uploadFile(MultipartFile file) throws Exception {
        return cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
        );
    }

    public void deleteFile(String publicId, String resourceType) {

        try {
            Map result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap("resource_type", resourceType)
            );

            String status = result.get("result").toString();

            if ("not found".equals(status)) {
                throw new CloudinaryNotFoundException();
            }

        } catch (
                CloudinaryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CloudinaryServiceException();
        }
    }
}