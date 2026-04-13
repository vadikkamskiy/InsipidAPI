package com.insipidcake.API.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.insipidcake.API.entity.Song;
import com.insipidcake.API.repository.SongsRepository;

@Service
public class SearchService {
    private final SongsRepository songsRepository;

    public SearchService(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public List<Song> getLast(){
        return null;
    }

    public List<Song> search(String query){
        return null;
    }

    public List<com.insipidcake.API.model.Song> webSearch(String query) {
        List<com.insipidcake.API.model.Song> result = new ArrayList<>();
        System.out.println("find: " + query);
        try {
            String url = System.getenv("MUSIC_WEB") + "/search?q=" + query;
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .maxBodySize(0)
                    .get();
            Elements tracks = doc.select("li.tracks__item.track.mustoggler");

            for (Element track : tracks) {
                JsonObject obj = JsonParser
                        .parseString(track.attr("data-musmeta"))
                        .getAsJsonObject();
                result.add(new com.insipidcake.API.model.Song(
                    obj.get("artist").getAsString(),
                    obj.get("title").getAsString(),
                    track.selectFirst("div.track__fulltime") != null
                            ? track.selectFirst("div.track__fulltime").text()
                            : "Unknown",
                    obj.get("url").getAsString(),
                    obj.get("img").getAsString()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
