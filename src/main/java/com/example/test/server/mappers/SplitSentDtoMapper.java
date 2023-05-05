package com.example.test.server.mappers;

import com.example.test.client.dto.SentDTO;

import java.util.ArrayList;
import java.util.List;

public class SplitSentDtoMapper {
    public List<SentDTO> split(SentDTO sentDTO) {
        List<SentDTO> outList = new ArrayList<>();
        int sizeSplitList = (int) Math.ceil((sentDTO.getTexts().size()) / 10.0);
        int mark = 0;
        while (mark < sentDTO.getTexts().size()) {
            SentDTO splitDTO = new SentDTO();
            splitDTO.setTexts(sentDTO.getTexts().subList(mark, mark + sizeSplitList));
            splitDTO.setSourceLanguageCode(sentDTO.getSourceLanguageCode());
            splitDTO.setTargetLanguageCode(sentDTO.getTargetLanguageCode());
            outList.add(splitDTO);
            mark = mark + sizeSplitList;
            if (outList.size() == (sentDTO.getTexts().size() / sizeSplitList)) {
                sizeSplitList = sentDTO.getTexts().size() % sizeSplitList;
            }
        }
        return outList;
    }
}
