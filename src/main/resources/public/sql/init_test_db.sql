CREATE TABLE IF NOT EXISTS testwebshop
(
  ws_id SERIAL PRIMARY KEY,
  webshop_key VARCHAR UNIQUE
);


CREATE TABLE IF NOT EXISTS testwebshopAnalytics
(
  an_id SERIAL PRIMARY KEY,
  webshop_id INT REFERENCES testwebshop(ws_id),
  session_id VARCHAR NOT NULL,
  visit_start TIMESTAMP NOT NULL,
  visit_end TIMESTAMP NOT NULL,
  location VARCHAR NOT NULL,
  amount FLOAT NULL,
  currency VARCHAR NULL
);