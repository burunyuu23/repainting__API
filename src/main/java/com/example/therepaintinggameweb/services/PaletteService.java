package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.responses.palette.GetPaletteResponseDTO;
import com.example.therepaintinggameweb.entities.Palettes;
import com.example.therepaintinggameweb.exceptions.NotFoundPaletteException;
import com.example.therepaintinggameweb.logic.Color;
import com.example.therepaintinggameweb.repos.PalettesRepo;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Data
public class PaletteService {
    protected final PalettesRepo palettesRepo;
    protected final ModelMapper modelMapper;
    protected final Gson gson;

    public GetPaletteResponseDTO getPalette(Long id) {
        Palettes palette = palettesRepo.findById(id).orElseThrow(NotFoundPaletteException::new);

        String paletteColors = palette.getPalette();

        List<LinkedTreeMap<String, Object>> objectList = (List<LinkedTreeMap<String, Object>>) gson.fromJson(paletteColors, List.class);

        List<Color> colors = objectList.stream().map(obj -> new Color(((Double) obj.get("id")).intValue(), (String) obj.get("hexCode"))).toList();

        return new GetPaletteResponseDTO(colors.toArray(new Color[0]));
    }
}
