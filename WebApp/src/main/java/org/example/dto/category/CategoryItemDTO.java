package org.example.dto.category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryItemDTO {
    private int id;
    private String name;
    private String description;
    private String imageNormal;
    private String imageSmall;
    private String imageLarge;
    private String imageAvatar;

}
