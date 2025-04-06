CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE users(
id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
full_name VARCHAR(100) NOT NULL,
username VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL,
role VARCHAR(10) NOT NULL,
removed_at TIMESTAMP
);

CREATE TABLE task(
id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
title VARCHAR(100) NOT NULL,
description TEXT NOT NULL,
student_id UUID REFERENCES users(id),
teacher_id UUID REFERENCES users(id),
removed_at TIMESTAMP
);
CREATE TABLE submission (
id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
teacher_id UUID REFERENCES users(id),
student_id UUID REFERENCES users(id),
task_id UUID REFERENCES task(id),
answer TEXT NOT NULL,
removed_at TIMESTAMP NULl
);

CREATE TABLE grade(
id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
value FLOAT NOT NULL,
feedback VARCHAR(500) NULL,
submission_id UUID REFERENCES submission(id),
removed_at TIMESTAMP
);