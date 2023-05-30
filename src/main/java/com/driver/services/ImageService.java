package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Optional<Blog> optionalBlog = blogRepository2.findById(blogId);
        Blog blog = new Blog();

        Image image = new Image();
        image.setDescription(description);
        image.setDimension(dimensions);
        image.setBlog(blog);
        blog.getImageList().add(image);
        Blog savedBlog = blogRepository2.save(blog); // save both blog and image;

        int lastImage = savedBlog.getImageList().size()-1;
        return savedBlog.getImageList().get(lastImage);
    }

    public void deleteImage(Integer id){
        Optional<Image> optionalImage = imageRepository2.findById(id);
        Image image = optionalImage.get();

        Blog blog = image.getBlog();
        blog.getImageList().remove(image);
        imageRepository2.delete(image);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        int firstCharacter = Character.getNumericValue(screenDimensions.charAt(0));
        int lastCharacter = Character.getNumericValue(screenDimensions.charAt(2));
        int totalDimension = firstCharacter*lastCharacter;

        Optional<Image> optionalImage = imageRepository2.findById(id);
        Image image = optionalImage.get();
        String dimension = image.getDimension();
        int len = Character.getNumericValue(dimension.charAt(0));
        int breath = Character.getNumericValue(dimension.charAt(2));
        int area = len * breath;

        return (totalDimension/area);
    }
}
