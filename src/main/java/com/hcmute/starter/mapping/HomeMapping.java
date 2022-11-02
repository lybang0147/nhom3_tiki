package com.hcmute.starter.mapping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class HomeMapping {
    public void getListQuickLink(List<QuickLink> list){
        list.add(new QuickLink(1,"https://salt.tikicdn.com/ts/upload/7b/fc/54/777d24de8eff003bda7a8d5f4294f9a8.gif","Mua sắm có lời",""));
        list.add(new QuickLink(2,"https://salt.tikicdn.com/cache/w100/ts/upload/9c/ca/37/d6e873b1421da32b76654bb274e46683.png.webp","Siêu sale 7.7",""));
        list.add(new QuickLink(3,"https://salt.tikicdn.com/cache/w100/ts/upload/68/9c/2f/6fc82a9a9713a2c2b1968e9760879f6e.png.webp","Đi chợ siêu tốc",""));
        list.add(new QuickLink(4,"https://salt.tikicdn.com/cache/w100/ts/upload/73/e0/7d/af993bdbf150763f3352ffa79e6a7117.png.webp","Dóng tiền, nạp thẻ",""));
        list.add(new QuickLink(5,"https://salt.tikicdn.com/cache/w100/ts/upload/ff/20/4a/0a7c547424f2d976b6012179ed745819.png.webp", "Mua bán ASA/XU", ""));
        list.add(new QuickLink(6,"https://salt.tikicdn.com/cache/w100/ts/upload/73/50/e1/83afc85db37c472de60ebef6eceb41a7.png.webp","Mã giảm giá",""));
        list.add(new QuickLink(7,"https://salt.tikicdn.com/cache/w100/ts/upload/ef/ae/82/f40611ad6dfc68a0d26451582a65102f.png.webp","Bảo hiểm 360",""));
        list.add(new QuickLink(8,"https://salt.tikicdn.com/cache/w100/ts/upload/99/29/ff/cea178635fd5a24ad01617cae66c065c.png.webp","Giảm đến 50%",""));
        list.add(new QuickLink(9,"https://salt.tikicdn.com/cache/w100/ts/upload/99/29/ff/cea178635fd5a24ad01617cae66c065c.png.webp","Hoàn tiền 15%",""));
        list.add(new QuickLink(10,"https://salt.tikicdn.com/cache/w100/ts/upload/4a/b2/c5/b388ee0e511889c83fab1217608fe82f.png.webp","Ưu đãi thanh toán",""));
    }
    public void getListEvent(List<QuickLink> list){
        list.add(new QuickLink(1,"https://salt.tikicdn.com/cache/w1080/ts/banner/4e/ec/28/71aa878fcf6a4d5ae2b2579ac6c0ba42.png.webp","",""));
        list.add(new QuickLink(2,"https://salt.tikicdn.com/cache/w1080/ts/banner/d0/9b/ff/dd99bd105dff7cd7dcff6b72926f11b7.png.webp","",""));
        list.add(new QuickLink(3,"https://salt.tikicdn.com/cache/w1080/ts/banner/06/03/99/71526328c1f55f8343e65d47e4dec24b.png.webp","",""));
        list.add(new QuickLink(4,"https://salt.tikicdn.com/cache/w1080/ts/banner/a1/b1/c6/17a7b69dd325f7d06e1d663ccb7a8cab.png.webp","",""));
        list.add(new QuickLink(5,"https://salt.tikicdn.com/cache/w1080/ts/banner/07/be/16/fc5457a1f72bbbb8fff3d82bfc682c9f.jpg.webp", "", ""));
    }
    public void getListCategorySpecify(List<QuickLink> list){
//        {
//            "id": 6,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/7e/21/b3/eb44ca47ec51e52b68f851cc1a4202a8.jpg.webp",
//                "alt": "Kẹo",
//                "link": ""
//        },
//        {
//            "id": 7,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/79/78/7e/a70f1b4320b7d2fd31897a7c4efc2f34.jpg.webp",
//                "alt": "Truyện tranh, Manga, Comic",
//                "link": ""
//        },
//        {
//            "id": 8,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/22/cb/a9/524a27dcd45e8a13ae6eecb3dfacba7c.jpg.webp",
//                "alt": "Sách tư duy - Kỹ năng sống",
//                "link": ""
//        },
//        {
//            "id": 9,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/7c/e8/34/4d3636aadb471cad0bf2f45d681e4f23.jpg.webp",
//                "alt": "Truyện ngắn - Tản văn",
//                "link": ""
//        },
//        {
//            "id": 10,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/e7/37/58/0ddcb4044c51e371aa54ac0d0bd00729.jpg.webp",
//                "alt": "Bàn ghế làm việc",
//                "link": ""
//        },
//        {
//            "id": 11,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/5e/18/24/2a6154ba08df6ce6161c13f4303fa19e.jpg.webp",
//                "alt": "Tiểu thuyết",
//                "link": ""
//        },
//        {
//            "id": 12,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/90/45/34/d5de0766b91469beb94da3ea7af202fd.jpg.webp",
//                "alt": "Light Novel",
//                "link": ""
//        },
//        {
//            "id": 13,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/e1/04/31/7763d9035552760f627c34acfec0e12f.jpg.webp",
//                "alt": "Sách Học Tiếng Anh",
//                "link": ""
//        },
//        {
//            "id": 14,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/4e/18/1e/aa90c76a8066d751b77deb17422ba1e0.jpg.webp",
//                "alt": "Khác",
//                "link": ""
//        },
//        {
//            "id": 15,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/c3/0c/4a/263d041ad1099b75fe603397cb31c3ff.jpg.webp",
//                "alt": "Tủ",
//                "link": ""
//        },
//        {
//            "id": 16,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/25/8c/35/d9081d7f2905df3cf4d1700f180b85a3.jpg.webp",
//                "alt": "Phụ kiện nhà bếp khác",
//                "link": ""
//        },
//        {
//            "id": 17,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/27/02/b7/49104866e1a499616f0efffe65dad186.png.webp",
//                "alt": "Kệ & tủ",
//                "link": ""
//        },
//        {
//            "id": 18,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/dc/14/f1/32d400ab6b71d8cef6938b9a36baf53a.jpg.webp",
//                "alt": "Cây cảnh",
//                "link": ""
//        },
//        {
//            "id": 19,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/e1/8c/3c/9f3524fd1f998f292ec40da6647a7e80.jpg.webp",
//                "alt": "Bình giữ nhiệt",
//                "link": ""
//        },
//        {
//            "id": 20,
//                "image": "https://salt.tikicdn.com/cache/w100/ts/product/96/46/60/096b3ef6d9265138abb024dd0a51ff15.jpg.webp",
//                "alt": "Kem dưỡng da",
//                "link": ""
//        }
        list.add(new QuickLink(1,
                "https://salt.tikicdn.com/cache/w100/ts/category/1e/8c/08/d8b02f8a0d958c74539316e8cd437cbd.png.webp",
                "NGON",
                "/ngon"));
        list.add(new QuickLink( 2,
                "https://salt.tikicdn.com/cache/w100/ts/product/0c/b8/11/6c14b804e2649e1f7162f4aef27d1648.jpg.webp",
                "Giày thể thao",
                ""));
        list.add(new QuickLink(3,
                "https://salt.tikicdn.com/cache/w100/ts/product/ad/50/99/93c55f64df94b3809e13e0648eec55f2.jpg.webp",
                "Balo",
                ""));
        list.add(new QuickLink( 4,
                "https://salt.tikicdn.com/cache/w100/ts/product/35/6c/4b/709aef22ee52628dcdbdc857ba1bc46c.jpg.webp",
                "Điện thoại Smartphone",
                ""));
        list.add(new QuickLink( 5,
                "https://salt.tikicdn.com/cache/w100/ts/product/15/d5/1d/64a37269a97a049337a0de82152fd43c.jpg.webp",
                "Nước giặt",
                ""));

    }

    @Data
    @Getter
    @Setter
    public class QuickLink{
        private int id;
        private String alt;
        private String link;
        private String image;

        public QuickLink(int id, String image, String alt, String link) {
            this.id = id;
            this.alt = alt;
            this.link = link;
            this.image = image;
        }
    }
}
