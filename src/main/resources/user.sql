CREATE TABLE `user`
(
    `id`   int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(25) COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `user`
VALUES ('1', '张三'),
       ('2', '李四'),
       ('3', '王五'),
       ('4', '赵六'),
       ('5', '田七'),
       ('6', '周八');
