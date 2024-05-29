--liquibase formatted sql

--changeset adagridjan:1.0.create-table-student stripComments:false splitStatements:false runAlways:false runOnChange:true failOnError:true
CREATE TABLE IF NOT EXISTS student
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(128) NOT NULL,
    address VARCHAR(255) NOT NULL
);

COMMENT ON TABLE public.student IS 'Таблица для хранения студентов';
COMMENT ON COLUMN public.student.id IS 'Первичный ключ. Уникальный идентификатор записи';
COMMENT ON COLUMN public.student.name IS 'Имя студента';
COMMENT ON COLUMN public.student.address IS 'Адрес проживания студента';


--changeset adagridjan:1.0.create-table-execution_time_method stripComments:false splitStatements:false runAlways:false runOnChange:true failOnError:true
CREATE TABLE IF NOT EXISTS execution_time_method
(
    id          BIGSERIAL PRIMARY KEY,
    created_at  timestamp with time zone DEFAULT now() NOT NULL,
    method_name VARCHAR(255)                           NOT NULL,
    time_taken  BIGINT                                 NOT NULL
);

COMMENT ON TABLE public.execution_time_method IS 'Таблица для хранения студентов';
COMMENT ON COLUMN public.execution_time_method.id IS 'Первичный ключ. Уникальный идентификатор записи';
COMMENT ON COLUMN public.execution_time_method.created_at IS 'Дата создания. Не изменяется после создания';
COMMENT ON COLUMN public.execution_time_method.method_name IS 'Имя метода, который отработал';
COMMENT ON COLUMN public.execution_time_method.time_taken IS 'Время выполнения метода в наносекундах';