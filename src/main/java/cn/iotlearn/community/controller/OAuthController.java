package cn.iotlearn.community.controller;

import cn.iotlearn.community.Provider.GithubProvider;
import cn.iotlearn.community.dto.AccessTokenDTO;
import cn.iotlearn.community.dto.GithubUserDTO;
import cn.iotlearn.community.model.User;
import cn.iotlearn.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class OAuthController {
    @Value("${githubAIP.Redirect_uri}")
    private String Redirect_uri;
    @Value("${githubAIP.Client_id}")
    private String Client_id;
    @Value("${githubAIP.Client_secret}")
    private String Client_secret;
    @Autowired
    private UserService userService;
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("callback")
    public String Callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response
    ) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setRedirect_uri(Redirect_uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUser = githubProvider.getGithubUserDTO(accessToken);
        System.out.println(githubUser.getName());
        try {
            if (githubUser.getName() != null){
                // 登陆成功
                // 把user放在session
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));

                user.setAvatarUrl(githubUser.getAvatar_url());
                userService.createOrUpdate(user);
                Cookie cookie = new Cookie("token",token);
                response.addCookie(cookie);
            }
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }catch (Exception ignored){
            System.out.println(ignored.getMessage());
            System.out.println(ignored.getCause());
            return "redirect:/";
        }
    }
    @GetMapping("logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response
    ){
//        删除session
        request.getSession().removeAttribute("user");
//        删除cookies
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
