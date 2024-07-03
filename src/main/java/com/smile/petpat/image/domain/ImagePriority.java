package com.smile.petpat.image.domain;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;

public enum ImagePriority {
    PRIORITY_1(1),
    PRIORITY_2(2),
    PRIORITY_3(3),
    PRIORITY_4(4),
    PRIORITY_5(5);

    private final int priority;

    ImagePriority(int priority){
        this.priority =priority;
    }

    public int getPriority(){
        return priority;
    }

    public static ImagePriority fromIndexToPriority(int index){
        for(ImagePriority p : ImagePriority.values()){
            if(p.getPriority()==index) return p;
        }
        throw new CustomException(ErrorCode.EXCEEDED_MAX_IMAGE_COUNT);
    }

}
