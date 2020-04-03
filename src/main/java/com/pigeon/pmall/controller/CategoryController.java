package com.pigeon.pmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pigeon.pmall.pojo.Category;
import com.pigeon.pmall.service.CategoryService;
import com.pigeon.pmall.util.ImageUtil;

@RestController
public class CategoryController {
    @Autowired CategoryService categoryService;
  
    @GetMapping("/categories")
    public PageInfo<Category> list(@RequestParam(value="start", defaultValue="1")int start, @RequestParam(value="size", defaultValue="5")int size) throws Exception {
    	
    	PageHelper.startPage(start, size, "id desc");
    	List<Category> categories = categoryService.list();
    	PageInfo<Category> page = new PageInfo<>(categories, 5);//5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
    	
		return page;
    }
    
    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id")int id) throws Exception {
    	Category c = categoryService.get(id);
    	return c;
    }
    
    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
//    	System.out.println("----------:"+bean);
    	categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }
    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,bean.getId()+".jpg");
//        System.out.println(file);
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
//        System.out.println(image);
        BufferedImage img = ImageUtil.change2jpg(file);
//        System.out.println(img);
        ImageIO.write(img, "jpg", file);
    }
    
    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id")int id, HttpServletRequest request) throws Exception {
    	categoryService.delete(id);
    	File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
    	//System.out.println(imageFolder);
    	File file = new File(imageFolder, id+".jpg");
    	file.delete();
    	return null;
    }
    
    @PutMapping("/categories/{id}") //debug一下，这里的id会注入到入参中的category中
    public Object update(MultipartFile image, Category bean, HttpServletRequest request) throws Exception {
//        String name = request.getParameter("name");
//        bean.setName(name);
//        System.out.println("------------:"+bean);
        categoryService.update(bean);
 
        if(image!=null) {
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }
    
}
