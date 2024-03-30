package com.example.seriousMall.controller;

import com.example.seriousMall.constant.ProductCategory;
import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.dto.ProductQueryParams;
import com.example.seriousMall.model.Employee;
import com.example.seriousMall.model.Product;
import com.example.seriousMall.repository.EmployeeRepository;
import com.example.seriousMall.repository.ProductRepository;
import com.example.seriousMall.service.ProductService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductController {
    private static String UPLOADED_FOLDER = ".//upload//";
    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/testUnBlock")
    public String testUnBlock() {
        return "test UnBlock";
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts(
            //get請求，字串位置沒有要求

            //查詢條件 filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序 sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset

    )
    {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setProductCategory(category);
        productQueryParams.setSort(sort);
        productQueryParams.setSearch(search);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        productQueryParams.setOrderBy(orderBy);

        List<Product> productList = productService.getAllProducts(productQueryParams);

        for (Product pro : productList) {
            System.out.println("??" + pro);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/showProductByPage")
    public ResponseEntity<List<Product>> showMarkerController(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize,

            @RequestParam(defaultValue = "createdDate") String orderBy
            ){


        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, orderBy));
        Page<Product> productPage = productService.findProduct(pageRequest);
        for (int i = 0; i < productPage.getContent().size(); i++) {
            System.out.println(productPage.getContent().get(i));
        }
        return ResponseEntity.status(HttpStatus.OK).body(productPage.getContent());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductInfo(@PathVariable Integer productId) {
        Product product = productService.getProductInfo(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductParams productParams) {
        Integer productId = productService.createProduct(productParams);
        Product product = productService.getProductInfo(productId);


        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductParams productParams,
                                                 @PathVariable Integer productId) {
        Product product = productService.updateProduct(productParams, productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
    }


    // 多個檔案上傳的接口
    @PostMapping("/api/upload/multi")
    @ResponseBody
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("files") MultipartFile[] uploadfiles) {


        // 取得檔案名稱
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("請選擇檔案!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("成功上傳 - "
                + uploadedFileName, HttpStatus.OK);

    }

    //將檔案儲存
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //繼續下一個檔案
            }
            String currentDirectory = System.getProperty("user.dir");
            System.out.println("当前工作目录：" + currentDirectory);



            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }

    }

    public static String getPictureByteArray(String inputPath) throws IOException {

        File inputFile = new File(inputPath);
        byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
        String encodeString = Base64.getEncoder().encodeToString(fileContent);
        System.out.println(encodeString);

        byte[] decodeFile = Base64.getDecoder().decode(encodeString);
        File outputFile = new File(inputFile
                .getParentFile()
                .getAbsolutePath() + File.pathSeparator + "test_image_copy.jpg");
        FileUtils.writeByteArrayToFile(outputFile, decodeFile);


//        String path = "";
//        FileInputStream fis = new FileInputStream(path);
////		byte[] buffer = new byte[fis.available()];   //第一種寫法
////		fis.read(buffer);
//
//        byte[]buffer =fis.readAllBytes(); //第二種寫法
//
//        fis.close();
        return "ss";
    }

    public static void main(String[] args) throws IOException {
        ProductController p = new ProductController();
        p.getPictureByteArray(".//upload//S__96862239.jpg");
    }


}

