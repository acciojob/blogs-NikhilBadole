package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    UserRepository userRepository1;

    public Blog createAndReturnBlog(Integer userId, String title, String content) {
        //create a blog at the current time
        Blog blog = new Blog();
        Optional<User> optionalUser = userRepository1.findById(userId);
        User user = optionalUser.get();

        blog.setTitle(title);
        blog.setContent(content);
        blog.setPubDate(new Date());
        blog.setUser(user);

        user.getBlogs().add(blog);
        userRepository1.save(user); // save both user and blog
        return user.getBlogs().get(user.getBlogs().size() - 1);
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
        Optional<Blog> optionalBlog = blogRepository1.findById(blogId);
        Blog blog = optionalBlog.get();
        User user = blog.getUser();
        user.getBlogs().remove(blog);  // remove blog from user also
        blogRepository1.delete(blog);
    }
}
