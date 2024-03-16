-- DROP SCHEMA public;

-- CREATE SCHEMA public AUTHORIZATION pg_database_owner;

-- DROP SEQUENCE public.addresses_seq;

CREATE SEQUENCE public.addresses_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.buyer_seq;

CREATE SEQUENCE public.buyer_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.reviews_seq;

CREATE SEQUENCE public.reviews_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.sellers_seq;

CREATE SEQUENCE public.sellers_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.stores_seq;

CREATE SEQUENCE public.stores_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;-- public.addresses definition

-- Drop table

-- DROP TABLE public.addresses;

CREATE TABLE public.addresses (
                                  id int8 NOT NULL,
                                  created_at timestamp(6) NOT NULL,
                                  deleted bool NULL,
                                  enabled bool NULL,
                                  last_modified_at timestamp(6) NOT NULL,
                                  address_details varchar(140) NULL,
                                  address_line varchar(35) NOT NULL,
                                  city varchar(60) NOT NULL,
                                  contact_phone varchar(20) NOT NULL,
                                  country_code varchar(3) NOT NULL,
                                  latitude numeric(9, 6) NOT NULL,
                                  longitude numeric(9, 6) NOT NULL,
                                  region varchar(60) NOT NULL,
                                  CONSTRAINT addresses_pkey PRIMARY KEY (id)
);


-- public.buyers definition

-- Drop table

-- DROP TABLE public.buyers;

CREATE TABLE public.buyers (
                               id int8 NOT NULL,
                               created_at timestamp(6) NOT NULL,
                               deleted bool NULL,
                               enabled bool NULL,
                               last_modified_at timestamp(6) NOT NULL,
                               birthdate date NULL,
                               email varchar(255) NOT NULL,
                               "name" varchar(255) NOT NULL,
                               phone varchar(20) NOT NULL,
                               surname varchar(255) NULL,
                               CONSTRAINT buyers_pkey PRIMARY KEY (id)
);


-- public.buyer_addresses definition

-- Drop table

-- DROP TABLE public.buyer_addresses;

CREATE TABLE public.buyer_addresses (
                                        buyer_id int8 NOT NULL,
                                        address_id int8 NOT NULL,
                                        CONSTRAINT uk_5mowqa36en5ep07xcruoho9sq UNIQUE (address_id),
                                        CONSTRAINT fkbkduud8f9c2753jfchqx2mtip FOREIGN KEY (buyer_id) REFERENCES public.buyers(id),
                                        CONSTRAINT fkn4qae8ihle9t5oknvrqv9u3cp FOREIGN KEY (address_id) REFERENCES public.addresses(id)
);


-- public.stores definition

-- Drop table

-- DROP TABLE public.stores;

CREATE TABLE public.stores (
                               id int8 NOT NULL,
                               created_at timestamp(6) NOT NULL,
                               deleted bool NULL,
                               enabled bool NULL,
                               last_modified_at timestamp(6) NOT NULL,
                               email varchar(255) NOT NULL,
                               "name" varchar(255) NOT NULL,
                               phone varchar(20) NOT NULL,
                               review_count int8 NOT NULL,
                               store_rating_average float4 NOT NULL,
                               store_type varchar(255) NOT NULL,
                               address_id int8 NOT NULL,
                               CONSTRAINT stores_pkey PRIMARY KEY (id),
                               CONSTRAINT stores_store_type_check CHECK (((store_type)::text = 'FOOD_STORE'::text)),
	CONSTRAINT uk_lrfcwj1khod8ht4kt90bdd70q UNIQUE (address_id),
	CONSTRAINT fkn6g5fy0yv9wg5lm00wsjmnfj1 FOREIGN KEY (address_id) REFERENCES public.addresses(id)
);


-- public.reviews definition

-- Drop table

-- DROP TABLE public.reviews;

CREATE TABLE public.reviews (
                                id int8 NOT NULL,
                                created_at timestamp(6) NOT NULL,
                                deleted bool NULL,
                                enabled bool NULL,
                                last_modified_at timestamp(6) NOT NULL,
                                experience varchar(255) NULL,
                                rating varchar(255) NOT NULL,
                                buyer_id int8 NULL,
                                store_id int8 NULL,
                                CONSTRAINT reviews_pkey PRIMARY KEY (id),
                                CONSTRAINT reviews_rating_check CHECK (((rating)::text = ANY ((ARRAY['EXCELLENT'::character varying, 'SATISFACTORY'::character varying, 'DECENT'::character varying, 'LACKING'::character varying, 'POOR'::character varying])::text[]))),
	CONSTRAINT fkg5v2uypi6rxq6647cqef2d7pt FOREIGN KEY (store_id) REFERENCES public.stores(id),
	CONSTRAINT fkqnai7a002aaapaafhxds7b6v5 FOREIGN KEY (buyer_id) REFERENCES public.buyers(id)
);


-- public.sellers definition

-- Drop table

-- DROP TABLE public.sellers;

CREATE TABLE public.sellers (
                                id int8 NOT NULL,
                                created_at timestamp(6) NOT NULL,
                                deleted bool NULL,
                                enabled bool NULL,
                                last_modified_at timestamp(6) NOT NULL,
                                birthdate date NULL,
                                email varchar(255) NOT NULL,
                                "name" varchar(255) NOT NULL,
                                phone varchar(20) NOT NULL,
                                surname varchar(255) NULL,
                                authority varchar(255) NOT NULL,
                                store_id int8 NULL,
                                CONSTRAINT sellers_authority_check CHECK (((authority)::text = 'ROLE_MANAGER'::text)),
	CONSTRAINT sellers_pkey PRIMARY KEY (id),
	CONSTRAINT fkauk004ru5vqiu1ubf3lt55bvq FOREIGN KEY (store_id) REFERENCES public.stores(id)
);