package com.pigeon.pmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pigeon.pmall.pojo.Product;
import com.pigeon.pmall.pojo.ProductImage;
import com.pigeon.pmall.service.ProductImageService;
import com.pigeon.pmall.service.ProductService;
import com.pigeon.pmall.util.ImageUtil;

@RestController
public class ProductImageController {
	
	@Autowired
	ProductImageService productImageService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/products/{pid}/productImages")
	public List<ProductImage> list(/* @RequestParam("type") */String type, @PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
 
        if(ProductImageService.TYPE_SINGLE.equals(type)) {
            List<ProductImage> singles =  productImageService.listSingleProductImages(product);
            return singles;
        }
        else if(ProductImageService.TYPE_DETAIL.equals(type)) {
            List<ProductImage> details =  productImageService.listDetailProductImages(product);
            return details;
        }
        else {
            return new ArrayList<>();
        }
    }
	
	@PostMapping("/productImages")
    public Object add(@RequestParam("pid") int pid, @RequestParam("type") String type, MultipartFile image, HttpServletRequest request) throws Exception {
        ProductImage bean = new ProductImage();
        //Product product = productService.get(pid);
        bean.setPid(pid);
        bean.setType(type);
         
        productImageService.add(bean);
        String folder = "img/";
        if(ProductImageService.TYPE_SINGLE.equals(bean.getType())){
            folder +="productSingle";
        }
        else{
            folder +="productDetail";
        }
        File  imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);           
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        if(ProductImageService.TYPE_SINGLE.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");    
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }      
         
        return bean;
    }
	
	@DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        ProductImage bean = productImageService.get(id);
        productImageService.delete(id);
 
        String folder = "img/";
        if(ProductImageService.TYPE_SINGLE.equals(bean.getType()))
            folder +="productSingle";
        else
            folder +="productDetail";
 
        File  imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        file.delete();
        if(ProductImageService.TYPE_SINGLE.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }
 
        return null;
    }
	
}
