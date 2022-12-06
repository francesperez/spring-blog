package com.codeup.springblog;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.Users;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;


import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBlogApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationTests {

    private Users testUser;

    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepository postsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {
        testUser = userDao.findByUsername("testUser");

        if (testUser == null) {
            Users newUser = new Users();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("123"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        httpSession = this.mvc.perform(post("/login").with(csrf())
                        .param("username", "testUser")
                        .param("password", "123"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts/index"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    @Test
    public void contextLoads() {
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive() throws Exception {
        assertNotNull(httpSession);
    }

    @Test
    public void testCreatePost() throws Exception {
        this.mvc.perform(
                post("/posts/create").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "test")
                .param("body", "for sale")
                .param("image", "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fth03.deviantart.net%2Fimages%2FPRE%2Fi%2F2003%2F37%2Fe%2F0%2FColor_Test_Pattern.jpg&f=1&nofb=1&ipt=74b66c85d34577b0be9edc355eb74b913cea74f94aa15f68ac5f599331467847&ipo=images"))
                .andExpect(status().is3xxRedirection());
    }


//    @Test
//    public void testEditPost() throws Exception {
//        // Gets the first post for tests purposes
//        Post existingPost = postsDao.findAll().get(0);
//
//        // Makes a Post request to /posts/{id}/edit and expect a redirection to the Post show page
//        this.mvc.perform(
//                        post("/posts/" + existingPost.getID() + "/edit").with(csrf())
//                                .session((MockHttpSession) httpSession)
//                                .param("title", "edited title")
//                                .param("body", "edited body")
//                                .param("image", "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fth03.deviantart.net%2Fimages%2FPRE%2Fi%2F2003%2F37%2Fe%2F0%2FColor_Test_Pattern.jpg&f=1&nofb=1&ipt=74b66c85d34577b0be9edc355eb74b913cea74f94aa15f68ac5f599331467847&ipo=images"))
//                .andExpect(status().is3xxRedirection());
//
//        // Makes a GET request to /ads/{id} and expect a redirection to the Ad show page
//        this.mvc.perform(get("/posts/show"))
//                .andExpect(status().isOk())
//                // Test the dynamic content of the page
//                .andExpect(content().string(containsString("edited title")))
//                .andExpect(content().string(containsString("edited body")))
//                .andExpect(content().string(containsString("https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fth03.deviantart.net%2Fimages%2FPRE%2Fi%2F2003%2F37%2Fe%2F0%2FColor_Test_Pattern.jpg&f=1&nofb=1&ipt=74b66c85d34577b0be9edc355eb74b913cea74f94aa15f68ac5f599331467847&ipo=images")));
//    }

//    @Test
//    public void testDeleteAd() throws Exception {
//        // Creates a test Ad to be deleted
//        this.mvc.perform(
//                        post("/posts/create").with(csrf())
//                                .session((MockHttpSession) httpSession)
//                                .param("title", "ad to be deleted")
//                                .param("description", "won't last long"))
//                .andExpect(status().is3xxRedirection());
//
//        // Get the recent Ad that matches the title
//        Post existingPost = postsDao.findById(7);
//
//        // Makes a Post request to /ads/{id}/delete and expect a redirection to the Ads index
//        this.mvc.perform(
//                        post("/posts/" + existingPost.getID() + "/delete").with(csrf())
//                                .session((MockHttpSession) httpSession)
//                                .param("id", String.valueOf(existingPost.getID())))
//                .andExpect(status().is3xxRedirection());
//    }
}
