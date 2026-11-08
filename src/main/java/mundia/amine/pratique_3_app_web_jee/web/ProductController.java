package mundia.amine.pratique_3_app_web_jee.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import mundia.amine.pratique_3_app_web_jee.entities.Product;
import mundia.amine.pratique_3_app_web_jee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/user/index")
    public String index(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("productList", products);
        return "products";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") Long id){
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/newProduct")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) return "new-product";
        productRepository.save(product);
        return "redirect:/admin/newProduct";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "login";
    }

    @GetMapping("/user/search")
    public String search(Model model, @RequestParam(name = "keyword") String keyword){
        List<Product> products = productRepository.findByNameContainsIgnoreCase(keyword);
        model.addAttribute("productList", products);
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/admin/editProduct")
    public String editProduct(Model model, @RequestParam(name = "id") Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/admin/updateProduct")
    public String updateProduct(@Valid Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "edit-product";
        productRepository.save(product);
        return "redirect:/user/index";
    }
}
