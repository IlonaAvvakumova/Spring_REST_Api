package com.crud.controller;
import com.crud.model.Event;
import com.crud.model.FileEntity;
import com.crud.model.User;
import com.crud.service.FileService;
import com.crud.service.UserService;
import com.crud.utils.HibernateSessionFactoryUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*@WebServlet(urlPatterns = "/api/v1/files/*", initParams = {
        @WebInitParam(name = "upload.location", value = "D:/java/home/avvakumova/REST_API_App/src/main/java/com/crud/files")
})*/
/*@MultipartConfig()*/
@Controller
@RequestMapping("/api/v1/files/*")
@Transactional
public class FileController extends HttpServlet {
    FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    ApplicationContext context = new AnnotationConfigApplicationContext("crud");

    @PutMapping("/create")
    @ResponseBody
    protected void create(@RequestBody FileEntity file, @RequestHeader Map<String, Integer> headers){
            /*String json = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            FileEntity file = new ObjectMapper().readValue(json, FileEntity.class);*/
           /* file.setFilePath(getServletConfig().getInitParameter("upload.location"));*/
            FileEntity fileEntity = fileService.create(file);
            UserService userService = context.getBean(UserService.class);
            List<Event> eventEntities = new ArrayList<>();
            User user = new User();
           /* Enumeration s2 = file.getHeaders("user_id");
            while (s2.hasMoreElements()) {
                Integer id = Integer.parseInt((String) s2.nextElement());
                user = userService.getById(id);
                Event event = new Event();
                event.setFileEntity(fileEntity);
                event.setUser(user);
                eventEntities.add(event);
                try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()
                ) {
                    Transaction tx1 = session.beginTransaction();
                    session.save(event);
                    tx1.commit();
                }
            }*/
        for (Map.Entry<String, Integer> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            user = userService.getById(entry.getValue());
            Event event = new Event();
            event.setFileEntity(fileEntity);
            event.setUser(user);
            eventEntities.add(event);
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.persist(event);
        }
            user.setEventEntities(eventEntities);
            userService.update(user);

        }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        int index = req.getRequestURI().lastIndexOf("/");
        String idAsString = req.getRequestURI().substring(index + 1);
        if(idAsString !=null && !idAsString.isEmpty()){
            Integer id = Integer.parseInt(idAsString);
            FileEntity byId = fileService.getById(id);
            String jsonString = objectMapper.writeValueAsString(byId);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            printWriter.print(jsonString);
            printWriter.flush();
        }else{
            List<FileEntity> files = fileService.getAll();
            for (FileEntity u : files
            ) {
                String jsonString = objectMapper.writeValueAsString(u);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                printWriter.print(jsonString);
                printWriter.flush();
            }
        }
        printWriter.close();
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int index = req.getRequestURI().lastIndexOf("/");
        String idAsString = req.getRequestURI().substring(index + 1);
        if(idAsString !=null && !idAsString.isEmpty()){
            Integer id = Integer.parseInt(idAsString);
            fileService.deleteById(id);

        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equals("/REST_API_App/api/v1/files/loading")) {
            Part filePart = req.getPart("filePath");
            File uploads = new File(getServletConfig().getInitParameter("upload.location"));
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File file = new File(uploads, fileName);
            InputStream input = filePart.getInputStream();
            Files.copy(input, file.toPath());
        }
    }
}
