CREATE TABLE public.users (
	id uuid NOT NULL,
	tg_id varchar(30) NOT NULL,
	chat_id varchar(30) NOT NULL,
	user_name varchar(100) NULL,
	first_name varchar(100) NULL,
	last_name varchar(100) NULL,
	is_bot bool NOT NULL,
	language_code varchar(10) NULL,
	create_date timestamp NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE public.portfolio (
	id uuid NOT NULL,
	user_id uuid NOT NULL,
	ticker varchar(30) NOT NULL,
	create_date timestamp NOT NULL,
	CONSTRAINT portfolio_pkey PRIMARY KEY (id)
);