-- --------------
-- Cursor example
-- --------------

-- Create sequences:
drop sequence TEMP_BASE_SEQ;
drop sequence TEMP_DERIVED_SEQ;

create sequence TEMP_BASE_SEQ
	start with 1
	nomaxvalue;

create sequence TEMP_DERIVED_SEQ
	start with 1
	nomaxvalue;


-- Create tables:
drop table TEMP_DERIVED;
drop table TEMP_BASE;

create table TEMP_BASE (
	base_id integer not null,
	tag varchar2(32) not null,
	entry_count integer default 0,
	constraint pk_base
		primary key( base_id )
);

create table TEMP_DERIVED (
	derived_id integer not null,
	base_id integer,
	tag varchar2(32),
	name varchar2(32) not null,
	constraint pk_derived
		primary key(derived_id)
);

-- Bind the sequences to tables:
create or replace trigger TEMP_BASE_INS 
	before insert on TEMP_BASE for each row
begin 
	select 
		TEMP_BASE_SEQ.nextval
	into
		:NEW.base_id
	from
		DUAL;
end TEMP_BASE_INS;
/
create or replace trigger TEMP_DERIVED_INS 
	before insert on TEMP_DERIVED for each row
begin 
	select 
		TEMP_DERIVED_SEQ.nextval
	into
		:NEW.derived_id
	from
		DUAL;
end TEMP_DERIVED_INS;
/

-- Prefill the tables:

insert into TEMP_DERIVED( tag, name ) values ( 't3', 'item 3.1');
insert into TEMP_DERIVED( tag, name ) values ( 't1', 'item 1.1');
insert into TEMP_DERIVED( tag, name ) values ( 't2', 'item 2.1');
insert into TEMP_DERIVED( tag, name ) values ( 't4', 'item 4.1');
insert into TEMP_DERIVED( tag, name ) values ( 't1', 'item 1.2');
insert into TEMP_DERIVED( tag, name ) values ( 't3', 'item 3.3');
insert into TEMP_DERIVED( tag, name ) values ( 't3', 'item 3.2');
insert into TEMP_DERIVED( tag, name ) values ( 't4', 'item 4.2');

commit;
/

-- ------------------------------------------
-- STEP 2: call the cursor for delete/update:
-- ------------------------------------------

create or replace procedure TEMP_AGGREGATE is
	v_id TEMP_BASE.base_id%TYPE;
	v_tag TEMP_BASE.tag%TYPE;
	v_count TEMP_BASE.entry_count%TYPE;
	cursor c is select tag, count(derived_id)
		from TEMP_DERIVED group by tag;
begin
	open c;
	delete from TEMP_BASE;
	loop
		fetch c into v_tag, v_count;
		exit when c%NOTFOUND;
		insert into TEMP_BASE( tag, entry_count ) values ( v_tag, v_count ) returning v_id;
	end loop;
	commit;
	close c;
end TEMP_AGGREGATE;
/


		

