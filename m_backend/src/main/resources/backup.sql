-- H2 2.3.230;
;
CREATE USER IF NOT EXISTS "SA" SALT 'cb8c65dac098e4a7' HASH '0eeaffd747971f398998f1f8917f6506d17ef726b726be81160117d5380abea5' ADMIN;
CREATE CACHED TABLE "PUBLIC"."BRAND"(
    "BRAND_IDX" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 5) NOT NULL,
    "CREATE_DT" TIMESTAMP(6) NOT NULL,
    "DELETE_DT" TIMESTAMP(6),
    "MODIFY_DT" TIMESTAMP(6) NOT NULL,
    "USE_YN" BOOLEAN NOT NULL,
    "VERSION" BIGINT NOT NULL,
    "BRAND_DESC" CHARACTER VARYING(255),
    "BRAND_IMAGE" CHARACTER VARYING(255),
    "BRAND_NAME" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."BRAND" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" PRIMARY KEY("BRAND_IDX");

INSERT INTO "PUBLIC"."BRAND" VALUES
                                 (1, TIMESTAMP '2024-07-24 20:24:21.559973', NULL, TIMESTAMP '2024-07-24 20:24:21.559973', TRUE, 0, 'brandA', NULL, 'A'),
                                 (2, TIMESTAMP '2024-07-24 20:24:21.559973', NULL, TIMESTAMP '2024-07-24 20:24:21.559973', TRUE, 0, 'brandB', NULL, 'B'),
                                 (3, TIMESTAMP '2024-07-24 20:24:21.559973', NULL, TIMESTAMP '2024-07-24 20:24:21.559973', TRUE, 0, 'brandC', NULL, 'C'),
                                 (4, TIMESTAMP '2024-07-27 07:41:00.359082', TIMESTAMP '2024-07-28 06:42:06.433349', TIMESTAMP '2024-07-27 21:13:06.969631', FALSE, 1, 'I Brand', NULL, 'I');
CREATE CACHED TABLE "PUBLIC"."CATEGORY"(
    "CATEGORY_IDX" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "CREATE_DT" TIMESTAMP(6) NOT NULL,
    "DELETE_DT" TIMESTAMP(6),
    "MODIFY_DT" TIMESTAMP(6) NOT NULL,
    "USE_YN" BOOLEAN NOT NULL,
    "VERSION" BIGINT NOT NULL,
    "CATEGORY_NAME" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."CATEGORY" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_31" PRIMARY KEY("CATEGORY_IDX");

INSERT INTO "PUBLIC"."CATEGORY" VALUES
                                    (1, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\c0c1\c758'),
                                    (2, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\c544\c6b0\d130'),
                                    (3, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\bc14\c9c0'),
                                    (4, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\c2a4\b2c8\cee4\c988'),
                                    (5, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\ac00\bc29'),
                                    (6, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\baa8\c790'),
                                    (7, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\c591\b9d0'),
                                    (8, TIMESTAMP '2024-07-24 20:24:21', NULL, TIMESTAMP '2024-07-24 20:24:21', TRUE, 0, U&'\c561\c138\c11c\b9ac');
CREATE CACHED TABLE "PUBLIC"."PRODUCT"(
    "PRODUCT_IDX" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 31) NOT NULL,
    "CREATE_DT" TIMESTAMP(6) NOT NULL,
    "DELETE_DT" TIMESTAMP(6),
    "MODIFY_DT" TIMESTAMP(6) NOT NULL,
    "USE_YN" BOOLEAN NOT NULL,
    "VERSION" BIGINT NOT NULL,
    "PRODUCT_DESC" CHARACTER VARYING(255),
    "PRODUCT_IMAGE" CHARACTER VARYING(255),
    "PRODUCT_NAME" CHARACTER VARYING(255),
    "PRODUCT_PRICE" FLOAT(53),
    "BRAND_IDX" BIGINT,
    "CATEGORY_IDX" BIGINT
);
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1" PRIMARY KEY("PRODUCT_IDX");

INSERT INTO "PUBLIC"."PRODUCT" VALUES
                                   (2, TIMESTAMP '2024-07-24 20:37:05.817706', NULL, TIMESTAMP '2024-07-24 20:37:05.817706', TRUE, 0, 'outer', NULL, 'outer_1', 1500.0, 1, 2),
                                   (3, TIMESTAMP '2024-07-24 20:37:32.394753', NULL, TIMESTAMP '2024-07-24 20:37:32.394753', TRUE, 0, 'pants', NULL, 'pants_1', 1200.0, 1, 3),
                                   (4, TIMESTAMP '2024-07-24 20:38:06.948281', NULL, TIMESTAMP '2024-07-24 20:38:06.948281', TRUE, 0, 'snickers', NULL, 'snickers_1', 3500.0, 1, 4),
                                   (5, TIMESTAMP '2024-07-24 20:38:21.650652', NULL, TIMESTAMP '2024-07-24 20:38:21.650652', TRUE, 0, 'bag', NULL, 'bag_1', 1400.0, 1, 5),
                                   (6, TIMESTAMP '2024-07-24 20:38:38.980228', NULL, TIMESTAMP '2024-07-24 20:38:38.980228', TRUE, 0, 'hat', NULL, 'hat_1', 2300.0, 1, 6),
                                   (7, TIMESTAMP '2024-07-24 20:38:52.369622', NULL, TIMESTAMP '2024-07-24 20:38:52.369622', TRUE, 0, 'socks', NULL, 'socks_1', 600.0, 1, 7),
                                   (8, TIMESTAMP '2024-07-24 20:39:18.646283', NULL, TIMESTAMP '2024-07-24 20:39:18.646283', TRUE, 0, 'accessory', NULL, 'accessory_1', 4400.0, 1, 8),
                                   (11, TIMESTAMP '2024-07-24 20:40:32.990405', NULL, TIMESTAMP '2024-07-24 20:40:32.990405', TRUE, 0, 'outer', NULL, 'outer_1', 3200.0, 2, 2),
                                   (12, TIMESTAMP '2024-07-24 20:40:40.747083', NULL, TIMESTAMP '2024-07-24 20:40:40.747083', TRUE, 0, 'outer', NULL, 'outer_1', 3700.0, 3, 2),
                                   (13, TIMESTAMP '2024-07-24 20:41:07.75824', NULL, TIMESTAMP '2024-07-24 20:41:07.75824', TRUE, 0, 'pants', NULL, 'pants_1', 1700.0, 2, 3),
                                   (14, TIMESTAMP '2024-07-24 20:41:16.73857', NULL, TIMESTAMP '2024-07-24 20:41:16.73857', TRUE, 0, 'pants', NULL, 'pants_1', 500.0, 3, 3),
                                   (15, TIMESTAMP '2024-07-24 20:41:46.118627', NULL, TIMESTAMP '2024-07-24 20:41:46.118627', TRUE, 0, 'snickers', NULL, 'snickers_1', 700.0, 2, 4),
                                   (16, TIMESTAMP '2024-07-24 20:42:01.037046', NULL, TIMESTAMP '2024-07-24 20:42:01.037046', TRUE, 0, 'snickers', NULL, 'snickers_1', 1200.0, 3, 4),
                                   (17, TIMESTAMP '2024-07-24 20:42:28.262108', NULL, TIMESTAMP '2024-07-24 20:42:28.262108', TRUE, 0, 'bag', NULL, 'bag_1', 3700.0, 2, 5),
                                   (18, TIMESTAMP '2024-07-24 20:42:47.723442', NULL, TIMESTAMP '2024-07-24 20:42:47.723442', TRUE, 0, 'bag', NULL, 'bag_1', 4200.0, 3, 5),
                                   (19, TIMESTAMP '2024-07-24 20:43:04.261888', NULL, TIMESTAMP '2024-07-24 20:43:04.261888', TRUE, 0, 'hat', NULL, 'hat_1', 1250.0, 2, 6),
                                   (20, TIMESTAMP '2024-07-24 20:43:11.757539', NULL, TIMESTAMP '2024-07-24 20:43:11.757539', TRUE, 0, 'hat', NULL, 'hat_1', 1000.0, 3, 6),
                                   (21, TIMESTAMP '2024-07-24 20:43:37.231341', NULL, TIMESTAMP '2024-07-24 20:43:37.231341', TRUE, 0, 'socks', NULL, 'socks_1', 300.0, 2, 7),
                                   (22, TIMESTAMP '2024-07-24 20:43:43.286351', NULL, TIMESTAMP '2024-07-24 20:43:43.286351', TRUE, 0, 'socks', NULL, 'socks_1', 450.0, 3, 7),
                                   (23, TIMESTAMP '2024-07-24 20:44:03.89468', NULL, TIMESTAMP '2024-07-24 20:44:03.89468', TRUE, 0, 'accessory', NULL, 'accessory_1', 1450.0, 2, 8),
                                   (24, TIMESTAMP '2024-07-24 20:44:09.42002', NULL, TIMESTAMP '2024-07-26 23:26:47.79073', TRUE, 1, 'accessory', NULL, 'accessory_1', 2450.0, 2, 8),
                                   (25, TIMESTAMP '2024-07-26 23:29:06.945685', NULL, TIMESTAMP '2024-07-26 23:29:06.945685', TRUE, 0, U&'\c591\b9d0\c785\b2c8\b2e4.', NULL, U&'\c2e0\addc\c591\b9d0', 2000.0, 3, 7),
                                   (26, TIMESTAMP '2024-07-27 07:38:31.435057', NULL, TIMESTAMP '2024-07-27 07:38:31.435057', TRUE, 0, U&'\c591\b9d0\c785', NULL, 'socks', 255.0, 2, 7),
                                   (27, TIMESTAMP '2024-07-27 07:39:22.93725', NULL, TIMESTAMP '2024-07-27 07:39:22.93725', TRUE, 0, 'ffff', NULL, 'rr', 4555.0, 1, 8),
                                   (30, TIMESTAMP '2024-07-28 06:41:45.917201', TIMESTAMP '2024-07-28 06:42:06.423951', TIMESTAMP '2024-07-28 06:41:45.917201', FALSE, 0, U&'\d06c\b9ac\c2a4\b9c8\c2a4 \ac70\ce58\c6a9 \c591\b9d0\c785\b2c8\b2e4.', NULL, U&'\d06c\b9ac\c2a4\b9c8\c2a4 \c591\b9d0', 2400.0, 4, 7);
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."FK8LXA31LCQ24P242BSPKXAJ2OX" FOREIGN KEY("BRAND_IDX") REFERENCES "PUBLIC"."BRAND"("BRAND_IDX") NOCHECK;
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."FKEWU5WQW9P0NHLOIL4YJQOM38J" FOREIGN KEY("CATEGORY_IDX") REFERENCES "PUBLIC"."CATEGORY"("CATEGORY_IDX") NOCHECK;
