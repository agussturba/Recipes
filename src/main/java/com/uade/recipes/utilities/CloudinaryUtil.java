package com.uade.recipes.utilities;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtil {
    private static final Cloudinary instance;

    static {
        instance = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "fransiciliano",
                "api_key", "828683531861771",
                "api_secret", "yzMcWnm6K8ipGKkjahVR0Gi5jeI"));

    }

    public static Cloudinary getInstance() {
        return instance;
    }
}
