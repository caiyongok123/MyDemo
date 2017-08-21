package com.example.cy.myapplication.entity;

import java.util.List;

/**
 * Created by cy on 2016/10/8.
 * 谷歌地图搜索周边结果
 */
public class GoogleSearchInfo {

    //public String next_page_token;
    //public List<?> html_attributions;
    /**
     * geometry : {"location":{"lat":22.543096,"lng":114.057865},"viewport":{"northeast":{"lat":22.7809313,"lng":114.3553162},"southwest":{"lat":22.3293893,"lng":113.7524414}}}
     * icon : https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png
     * id : 15a8ac8ebd04ae6c787d7a78504b8daa08853ce4
     * name : 深圳市
     * photos : [{"height":768,"html_attributions":["<a href=\"https://maps.google.com/maps/contrib/114439669949658382479/photos\">Shahid Awan<\/a>"],"photo_reference":"CoQBcwAAANbVPqrUWbQb92Kmef00o6Ur9rbHe2MQpIhj2WZg-G492NiBc8JNLtiijLy6GVWnIznnMpCsdT7VGurnBkl1y4aRfytJHr8Q7a9wTBbmUQkpxrpIyreL9dty8Zc9R-snmcXhIdPPRkIuE9HZPFkstFM31tYdiSgl8uak2iwiIejMEhDXqEHtDqdRT6JUCeiH22OiGhTsmNYgamJ60eDBFALG6fbRF5OQnw","width":1024}]
     * place_id : ChIJkVLh0Aj0AzQRyYCStw1V7v0
     * reference : CoQBdQAAAKrB09RO6mjm9MuOYc-4dtXju3lXp0nZtgpYm3O2orvTP5HS-3HVT8wiI-vdY6Zv4_yL_sW8Co4Arzt4vjRuJlBBE58GVuY7lCchN7xfOvIhnom1c9f3vfRKrw_x1ZF2ZHl6zTtTm5BjE6RzS6c61OLaxYSdekywtxKEnFWZaI_jEhA8jNXarbI62pzLzqJsjG7VGhTBHgkQb9hYcsiE8jxixNzXSM6K0A
     * scope : GOOGLE
     * types : ["locality","political"]
     * vicinity : 深圳市
     */



    public List<ResultsBean> results;

    public static class ResultsBean {
        /**
         * location : {"lat":22.543096,"lng":114.057865}
         * viewport : {"northeast":{"lat":22.7809313,"lng":114.3553162},"southwest":{"lat":22.3293893,"lng":113.7524414}}
         */

        public String formatted_address;
        public GeometryBean geometry;
        public String icon;
        public String id;
        public String name;
        public String place_id;
        public String reference;
        public String scope;
        public String vicinity;
        /**
         * height : 768
         * html_attributions : ["<a href=\"https://maps.google.com/maps/contrib/114439669949658382479/photos\">Shahid Awan<\/a>"]
         * photo_reference : CoQBcwAAANbVPqrUWbQb92Kmef00o6Ur9rbHe2MQpIhj2WZg-G492NiBc8JNLtiijLy6GVWnIznnMpCsdT7VGurnBkl1y4aRfytJHr8Q7a9wTBbmUQkpxrpIyreL9dty8Zc9R-snmcXhIdPPRkIuE9HZPFkstFM31tYdiSgl8uak2iwiIejMEhDXqEHtDqdRT6JUCeiH22OiGhTsmNYgamJ60eDBFALG6fbRF5OQnw
         * width : 1024
         */

        public List<PhotosBean> photos;
        public List<String> types;

        public static class GeometryBean {
            /**
             * lat : 22.543096
             * lng : 114.057865
             */

            public LocationBean location;
            /**
             * northeast : {"lat":22.7809313,"lng":114.3553162}
             * southwest : {"lat":22.3293893,"lng":113.7524414}
             */

            public ViewportBean viewport;

            public static class LocationBean {
                public double lat;
                public double lng;
            }

            public static class ViewportBean {
                /**
                 * lat : 22.7809313
                 * lng : 114.3553162
                 */

                public NortheastBean northeast;
                /**
                 * lat : 22.3293893
                 * lng : 113.7524414
                 */

                public SouthwestBean southwest;

                public static class NortheastBean {
                    public double lat;
                    public double lng;
                }

                public static class SouthwestBean {
                    public double lat;
                    public double lng;
                }
            }
        }

        public static class PhotosBean {
            public int height;
            public String photo_reference;
            public int width;
            public List<String> html_attributions;
        }
    }

    //"status" : "OK"
    public String status;

    public boolean isSuccess(){
        return "OK".equals(status);
    }
}
