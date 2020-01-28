package cn.iotlearn.community.Provider;

import cn.iotlearn.community.dto.AccessTokenDTO;
import cn.iotlearn.community.dto.GithubUserDTO;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String url = "https://github.com/login/oauth/access_token";
        OkHttpClient client = new OkHttpClient();
        String json = JSON.toJSONString(accessTokenDTO);
        RequestBody body = RequestBody.create(json,mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()){
            String accessToken = response.body().string();
            String token = accessToken.split("&")[0].split("=")[1];
            return token;
        }
    }
    public GithubUserDTO getGithubUserDTO(String accessToken){
        String url = "https://api.github.com/user?access_token="+accessToken;
        OkHttpClient client = new OkHttpClient();
        GithubUserDTO githubUserDTO = null;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String jsonStr = response.body().string();
            System.out.println(jsonStr);
            githubUserDTO = JSON.parseObject(jsonStr,GithubUserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return githubUserDTO;
    }
}
