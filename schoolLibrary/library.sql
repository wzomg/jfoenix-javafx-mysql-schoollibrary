/*
 Navicat Premium Data Transfer

 Source Server         : gdufe_library
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 服务器ip:3307
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 22/11/2019 20:34:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `BId` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书号',
  `BName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书名',
  `BAuthor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者',
  `BKind` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书类别',
  `BPrice` double(10, 2) NOT NULL COMMENT '价格',
  `BDate` date NOT NULL COMMENT '入库日期',
  `BPress` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '出版社',
  `BSummary` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '摘要',
  `BCnt` int(11) NOT NULL DEFAULT 0 COMMENT '被借次数',
  `BStatus` tinyint(4) NOT NULL DEFAULT 1 COMMENT '书刊状态',
  PRIMARY KEY (`BId`) USING BTREE,
  INDEX `BKind`(`BKind`) USING BTREE,
  CONSTRAINT `BKind` FOREIGN KEY (`BKind`) REFERENCES `tkind` (`TId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('bk00001', '马克思主义研究', '马克思主义研究学部 马克思主义研究编辑部', 'typebk01', 30.00, '2019-10-24', '北京:马克思主义研究编辑部', '本刊是以研究马克思主义整体理论体系为宗旨的大型学术理论刊物。', 0, 1);
INSERT INTO `book` VALUES ('bk00002', '马克思诗集', '马克思(德)', 'typebk01', 50.00, '2019-10-24', '天津:百花文艺出版社', '本书收诗歌110余首，几乎是马克思全部的诗歌创作。', 0, 0);
INSERT INTO `book` VALUES ('bk00003', '理性之谜', '(法)雨果·梅西耶,(法)丹·斯珀伯', 'typebk02', 40.00, '2019-10-24', '北京:中信出版集团股份有限公司', '理性让我们具有人性，让我们获取知识、变得睿智。', 10, 1);
INSERT INTO `book` VALUES ('bk00004', '信仰的动力', '(美)保罗·蒂利希', 'typebk02', 56.00, '2019-10-24', '中国轻工业出版社', '《信仰的动力》是美国当代著名哲学家、神学家保罗·蒂利希的代表作。', 1, 0);
INSERT INTO `book` VALUES ('bk00005', '数字人文:改变知识创新与分享的游戏规则', '(美)安妮·伯迪克', 'typebk03', 33.60, '2019-10-24', '北京:中国人民大学出版社', '数据、云计算、人工智能迅猛发展，影响着人类生活的方方面面，也不可避免地影响着人文学科研究、生产、传播的方式。', 0, 1);
INSERT INTO `book` VALUES ('bk00006', '无网不胜:中国社会科学网创办侧记', '周溯源', 'typebk03', 44.30, '2019-10-24', '北京:中国社会科学出版社', '要大力加强信息网络建设! 近一二十年互联网的发展一日千里\" 其功能和影响力日益强大\" 对图书报刊的冲击很大\" 甚至于有漫卷之势! ', 1, 0);
INSERT INTO `book` VALUES ('bk00007', '文史知识.2017年', '《文史知识》编辑部', 'typebk04', 30.40, '2019-10-24', '北京:中华书局', '本书包含了《文史知识》杂志2017全年1-12期的所有内容，设有特别关注、诗文欣赏、人物春秋、文化史知识、学林漫话、交流与比较、书画欣赏、经典释文等栏目。', 9, 1);
INSERT INTO `book` VALUES ('bk00008', '社会科学二十讲', '曹伯韩', 'typebk04', 40.40, '2019-10-24', '哈尔滨:哈尔滨出版社', '《社会科学二十讲》是一本面向普通读者的社会科学入门读物。作者以通俗流畅的笔触，运用谈话的形式，将社会、家庭、国家、民族四方面的内容娓娓道来。', 1, 0);
INSERT INTO `book` VALUES ('bk00009', '经济战争与战争经济:德国财政部长一战回忆录', '(德)卡尔·赫弗里希', 'typebk05', 27.80, '2019-10-24', '北京:台海出版社', '本书围绕世界大战时德国的战争手段和经济政策展开论述，阐述了两个概念：经济战争和战争经济。', 4, 1);
INSERT INTO `book` VALUES ('bk00010', '武器心学:武器意象的心理临床', '苑媛', 'typebk05', 30.50, '2019-10-24', '北京:北京师范大学出版社', '每一种武器都是一个文化符号，宛如一个个鲜活的生命，背后都有一段生动甚至神奇的故事，这些故事又凝炼成某种性格或某种精神。', 0, 1);
INSERT INTO `book` VALUES ('bk00011', '交往与市场:马克思交往理论研究', '栾文莲', 'typebk06', 40.00, '2019-10-24', '北京:社会科学文献出版社', '本书依据马克思的世界性普遍交往的理论，论述了国际交往和世界市场在当代的发展、发展中国家和中国融入世界市场的必要性和利弊，论述了社会主义国家进行改革开放和世界性普遍交往的关系。', 0, 1);
INSERT INTO `book` VALUES ('bk00012', '宏观经济学:第六版', '宏观经济学:第六版,(美)S.费希尔', 'typebk06', 40.00, '2019-10-24', '北京:中国人民大学出版社', '本书是一套近年来在国内外都深受欢迎的中级宏观经济学教程。', 0, 1);
INSERT INTO `book` VALUES ('bk00013', '文化与哲学', '宋洪云', 'typebk07', 44.70, '2019-10-24', '北京:知识产权出版社有限责任公司', '本书主要探讨哲学与文化的关系。', 0, 1);
INSERT INTO `book` VALUES ('bk00014', '匠人', '(美)理查德·桑内特', 'typebk07', 30.60, '2019-10-24', '上海:译文出版社', '《匠人》要探讨的是一种基本的人性冲动：纯粹为了把事情做好而好好工作的欲望。', 1, 1);
INSERT INTO `book` VALUES ('bk00015', '当代译学批判', '覃学岚', 'typebk08', 29.00, '2019-10-24', '北京:清华大学出版社', '本书针对对传统译论的质疑和颠覆进行再质疑和再颠覆，通过对这些理论的质疑对等、否定忠实的立论根据进行深刻的分析。', 0, 1);
INSERT INTO `book` VALUES ('bk00016', '语言学课堂:当哲学家遇到认知语言学', '(日)西村义树,(日)野矢茂树', 'typebk08', 40.40, '2019-10-24', '北京:商务印书馆', '本书讲述了一位热爱语言学的哲学家，向一位认知语言学家学习语言学的六次奇妙旅程。', 0, 1);
INSERT INTO `book` VALUES ('bk00017', '翻译教学原理及其文学应用研究', '赵丹', 'typebk09', 40.00, '2019-10-24', '北京:中国纺织出版社', '《翻译教学原理及其文学应用研究》围绕英汉翻译教学，对其原理及其在文学应用中的研究做深入剖析。', 0, 1);
INSERT INTO `book` VALUES ('bk00018', '写小说最重要的十件事', '(美)厄休拉·勒古恩', 'typebk09', 50.00, '2019-10-24', '南昌:江西人民出版社', '写作是一门手艺，技巧精湛者才能获得艺术的自由。世界科幻、奇幻大师厄休拉·勒古恩认为，创作就像在大海中航行，只有掌握真正的技巧航海，才能在暗礁和重重险阻间开拓属于自己的航道。', 1, 1);
INSERT INTO `book` VALUES ('bk00019', '艺术真理问题的哲学反思', '张科晓', 'typebk10', 40.00, '2019-10-24', '北京:光明日报出版社', '本书为学术著作，以“艺术真理问题”为研究主题，以海德格尔和阿多诺的艺术真理观之比较研究为重点，兼论康德、黑格尔、韦伯以及伽达默尔等哲学家的艺术真理观。', 1, 0);
INSERT INTO `book` VALUES ('bk00020', '透视法的起源', '王哲然', 'typebk10', 33.60, '2019-10-24', '北京:商务印书馆', '本书分古代的视觉科学、中世纪的视觉科学、透视法的诞生、透视法的体系化、视学与透视法的关联和差异共五章。', 4, 0);
INSERT INTO `book` VALUES ('bk00021', '让历史有“实践”:历史人类学思想之旅', '张小军', 'typebk11', 40.00, '2019-10-24', '北京:清华大学出版社', '本书梳理了历史人类学的理论方法及其在华南研究中的部分理论贡献，归纳了历史的实质论、主体论、过程论、实践论的理论框架，并尝试以人类学“实践理论”的视角来思考历史研究。', 0, 1);
INSERT INTO `book` VALUES ('bk00022', '西方史学史:古代、中世纪和近代', '(美)恩斯特·布赖萨赫', 'typebk11', 50.00, '2019-10-24', '北京:北京大学出版社', '本书是对西方史学的综合性回顾, 展现了西方史学自古希腊至现代的发展脉络, 系统讨论了西方史学史上的重要事件和学派源流。', 0, 1);
INSERT INTO `book` VALUES ('bk00023', '自然哲学的数学原理', '艾萨克.牛顿 ', 'typebk12', 0.00, '2019-09-18', '重庆出版社', '《自然哲学的数学原理》涉及天文、物理、生物、心理、政治、经济、法律与军事等领域。', 0, 1);
INSERT INTO `book` VALUES ('bk00024', '数学物理方法', '顾樵', 'typebk12', 68.90, '2019-10-15', '科学出版社', '《数学物理方法》可作为物理学、应用数学及相关理工科专业本科生与研究生的教材', 4, 1);
INSERT INTO `book` VALUES ('bk00025', '统计推断（英文版·原书第2版）', '雷奥奇·卡塞拉,罗杰 L.贝耶 ', 'typebk13', 87.00, '2019-09-04', '机械工业出版社', '本书并不假定在任何概率论的先修知识。通过正文与习题旁征博引，引进了大量近代统计处理的新技术和一些国内同类教材中不能见而广为使用的分布。', 0, 1);
INSERT INTO `book` VALUES ('bk00026', '数学分析(第二册)', '伍胜健', 'typebk13', 20.20, '2019-10-15', '北京大学出版社', '数学分析（第二册）》是综合性大学和高等师范院校数学系本科生数学分析课程的教材。', 6, 0);
INSERT INTO `book` VALUES ('bk00027', '地理学与生活', ' 阿瑟·格蒂斯', 'typebk14', 171.00, '2019-09-06', '北京联合出版公司', '本书共十三章，分四篇，囊括自然地理学、人口地理学、文化地理学、城市地理学等分支学科。', 6, 1);
INSERT INTO `book` VALUES ('bk00028', '大众天文学', 'C.弗拉马里翁', 'typebk14', 100.00, '2019-09-13', '北京大学出版社', '《大众天文学》是一部天文学科普名著，作者卡米伊·弗拉马里翁以文学的笔触将浩芒宇宙的绚烂与神奇展示给广大读者。', 0, 1);
INSERT INTO `book` VALUES ('bk00029', '分子生物学（原书第五版）', 'RobertF.Weaver', 'typebk15', 123.80, '2019-10-17', '科学出版社', '《分子生物学（原书第五版）》不仅及时增添了学科发展的全新成果与进展，而且对全书的图示与图解进行了更加清晰，更为明了的设计与阐释。', 6, 1);
INSERT INTO `book` VALUES ('bk00030', '物种起源', '达尔文', 'typebk15', 163.50, '2019-10-27', '北京大学出版社', '《物种起源》是达尔文著名的代表作，是奠定生物进化论重要的著作，也是对人类发展进程产生过广泛影响的巨著。', 0, 1);
INSERT INTO `book` VALUES ('bk00031', '内科学', '葛均波,徐永健,王辰 ', 'typebk16', 89.40, '2019-10-19', '人民卫生出版社', '由全国高等医药教材建设研究学组规划，临床医学专业教材评审委员会审定，院士专家把关，全国各医学院校知名专家教授编写，人民卫生出版社高质量出版。', 8, 1);
INSERT INTO `book` VALUES ('bk00032', '协和全科医师手册', '曾学军,张昀 ', 'typebk16', 45.60, '2019-09-13', '中国协和医科大学出版社', '临床工作是一项既需要有大量理论知识，又必须掌握许多技能及治疗原则的实践科学。', 0, 1);
INSERT INTO `book` VALUES ('bk00033', '塑料温室大棚设计与建设', '高和林,赵斌,贾建国', 'typebk17', 38.20, '2019-09-20', '中国农业出版社', '讲解了关于农村温室大棚建造与使用的问题，具有很强的针对性、实用性和可操作性。', 0, 1);
INSERT INTO `book` VALUES ('bk00034', '软枣猕猴桃栽培与加工技术', '艾军', 'typebk17', 11.80, '2019-09-13', '中国农业出版社', '结合笔者的研究和生产实践，我们总结了近年来相关领域的技术成果编成《软枣猕猴桃栽培与加工技术》', 0, 1);
INSERT INTO `book` VALUES ('bk00035', '电工从入门到精通', '吴瑛', 'typebk18', 80.10, '2019-09-06', '化学工业出版社', '本书是专门介绍电工基础知识及技能的图书，特色就是通过学习可以将电工入门的专业知识、实操技能在短时间内全部掌握。', 9, 1);
INSERT INTO `book` VALUES ('bk00036', '电力电子学', '拉什德', 'typebk18', 131.10, '2019-09-13', '机械工业出版社', '本书涵盖了电力电子技术的基本内容，运用自下而上的方法，突出自器件到系统设备的介绍方法，着重分析技术应用。', 30, 0);
INSERT INTO `book` VALUES ('bk00037', '交通运输概论 ', '万明', 'typebk19', 27.70, '2019-09-13', '人民交通出版社', '交通运输概论》基于通识理念，扼要介绍了交通运输科学的相关知识。', 0, 1);
INSERT INTO `book` VALUES ('bk00038', '智慧交通实践', '蔡文海', 'typebk19', 80.10, '2019-09-06', '人民邮电出版社', '本书是站在第四次工业革命的角度，向读者介绍中国的智慧交通建设。通过宏观的视角，使读者了解城市对中国经济转型的重要性。', 0, 0);
INSERT INTO `book` VALUES ('bk00039', '航空航天概论', '徐江华', 'typebk20', 21.20, '2019-10-23', '北京航空航天大学出版社', '《航空航天概论（第2版）》以简明的内容和丰富的图片帮助读者了解航空航天基础知识，建立航空航天基本概念，树立热爱航空、航空报国的远大志向。', 4, 1);
INSERT INTO `book` VALUES ('bk00040', '飞行原理（新版）', '邢琳琳', 'typebk20', 27.00, '2019-10-11', '北京航空航天大学出版社', '本书对飞行原理及空气动力学知识进行了有效的梳理,同时整合了航空手册及私用驾驶员、商用驾驶员及仪表等级考试的核心知识点', 1, 1);
INSERT INTO `book` VALUES ('bk00041', '现代环境科学概论', '卢昌义', 'typebk21', 29.20, '2019-10-20', '厦门大学出版社', '《现代环境科学概论（第二版）》共分九章，简明扼要地介绍了当前环境科学中所包括的人口、能源、资源、粮食、污染、全球变化、生态安全等问题的最新资料', 0, 1);
INSERT INTO `book` VALUES ('bk00042', '水和废水监测分析方法(第四版)', '国家环保总局', 'typebk21', 57.00, '2019-10-24', '中国环境科学出版社', '国家环境保护总局规划与财务司和科技标准司，要求中国环境监测总站组织全国有关的监测技术人员对《水和废水监测分析方法》（第三版）进行修订再版。', 0, 1);
INSERT INTO `book` VALUES ('bk00043', '工程数学', '王玉清,刘桃凤,杨云霞,韩晓欣', 'typebk22', 42.20, '2019-10-22', '天津大学出版社', '该书的编写本着“以应用为目的”的原则，在保证科学性的基础上，特别注重对学生基本运算能力，分析问题、解决问题能力的培养，同时加深其对概念的深刻理解。', 43, 1);
INSERT INTO `book` VALUES ('bk00044', '我相信中国的未来', '梁晓声', 'typebk22', 39.00, '2019-10-17', '中国青年出版社', '历尽沧桑，依然满怀热望。被誉为“中国良心”、“青年导师”的著名作家梁晓声洞察家国人生的力作，52篇文稿既有对当下中国社会的全景深邃思考，也有对个人心路历程的深情追忆', 2, 0);
INSERT INTO `book` VALUES ('bk00045', '百年孤独', ' [哥伦比亚] 加西亚·马尔克斯', 'typebk09', 39.50, '2019-06-11', '北京邮电出版社', '《百年孤独》是魔幻现实主义文学的代表作，描写了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。', 0, 1);
INSERT INTO `book` VALUES ('bk00046', '三体', '刘慈欣', 'typebk09', 23.00, '2018-10-01', '重庆出版社', '文化大革命如火如荼进行的同时。军方探寻外星文明的绝秘计划“红岸工程”取得了突破性进展。但在按下发射键的那一刻，历经劫难的叶文洁没有意识到，她彻底改变了人类的命运。地球文明向宇宙发出的第一声啼鸣，以太阳为中心，以光速向宇宙深处飞驰……\n四光年外，“三体文明”正苦苦挣扎——三颗无规则运行的太阳主导下的百余次毁灭与重生逼迫他们逃离母星。而恰在此时。他们接收到了地球发来的信息。在运用超技术锁死地球人的基础科学之后。三体人庞大的宇宙舰队开始向地球进发……', 0, 1);
INSERT INTO `book` VALUES ('bk00047', '爱丽丝漫游奇境记', '刘易斯·卡罗尔', 'typebk09', 142.40, '2019-11-03', '江苏凤凰文艺出版社', '　一本书没有图画和对话，那有什么好看的？”\n　　姐姐在安心看书，爱丽丝却感到非常厌烦。这时，一只粉红眼睛的兔子从爱丽丝身边跑过，吸引了她所有的注意力。\n　　爱丽丝追着兔子，一路奔跑，掉进了兔子洞里，开启了一场奇妙的冒险……\n　　在人类文学史上，《爱丽丝漫游奇境记》从来不是简单的童话故事。它将一个小女孩奇境历险的故事变成了一个取之不尽的宝藏，成为无数文艺创作的灵感来源，被引用率仅次于莎士比亚作品。', 0, 1);
INSERT INTO `book` VALUES ('bk00048', '边城', '沈从文 ', 'typebk09', 21.90, '2019-11-06', '江苏人民出版社', '《边城（纪念版）》是沈从文颇负盛名的代表作品，以20世纪30年代川湘交界的边城小镇茶峒为背景，以兼具抒情诗和小品文的优美笔触，描绘了湘西边城淳朴的世道民风和天然的生活状态。语言古朴清新，寄托着从文先生关于“美”与“爱”的美学理想，彰显了人性的至真、至善与至美。', 0, 1);
INSERT INTO `book` VALUES ('bk00049', '鲁迅文学全集', '鲁迅', 'typebk09', 238.40, '2019-11-05', '群言出版社', '鲁迅是中国现代伟大的文学家、思想家。他是一位高产作家，涉足的领域也很广泛。全套《鲁迅全集》总计有600多万字，共20卷。作品类型包括文学创作、古籍校辑、文艺理论、翻译作品等。\n　　诚如蔡元培所说：“鲁迅著作方面较多，蹊径独辟，为后学开示无数法门。”但是对于一般的读者而言，一部《鲁迅全集》实在是太过繁多。鲁迅的所有作品中，以文学作品zui为一般读者所了解和喜爱。本套书收全了鲁迅所写的小说、杂文、散文和部分诗歌。编校的底本采用的是1938年“鲁迅先生纪念委员会”主持编订的《鲁迅全集》。在编辑过程中，尽可能保留了原版的样貌。', 0, 1);
INSERT INTO `book` VALUES ('bk00050', '三毛全集', '三毛 ', 'typebk09', 35.00, '2019-11-03', '北京十月文艺出版社', '她以一支笔坚持看守个人文字上的简单和朴素；从遥远的撒哈拉到敦煌戈壁，她不随波逐流，也不诠释人生，只做生活的见证者；她是我们心中浪漫、洒脱、真性情的永远的三毛，永恒的传奇。在三毛的内心深处，撒哈拉沙漠是梦中情人，是不能解释的，属于前世回忆似的乡愁，莫名其妙，她毫无保留地把自己交给了那一片陌生的大地；搁笔十年后，她首次以“三毛”的笔名，写下十余篇脍炙人口的散文，造就了流浪文学，畅销三十余年热度不减，并唤起了无数人心中的撒哈拉之梦。', 0, 1);
INSERT INTO `book` VALUES ('bk00051', '且借纸遁', '葛兆光', 'typebk07', 37.40, '2019-11-03', ' 广西师范大学出版社', '新时代的旧读者。葛兆光教授在学问上求新求变，不断开辟新领域；读书习惯却多少显得有些“保守”，还在遵循三十多年前所受的学术训练，在日记本和电脑上做笔记和摘抄。在这些读书笔记中，我们既能发现他一些学术观点的萌芽，也会看到专业论文里无法容纳的思考成果。', 0, 1);
INSERT INTO `book` VALUES ('bk00052', '乡土中国', '费孝通', 'typebk11', 38.00, '2015-09-09', '上海人民出版社', '著名社会学家费孝通教授，一生行行重行行，实地调查和考察总结中国农村经济发展的各种模式，写下了诸多不朽篇章。本书推出的是学界共认的中国乡土社会传统文化和社会结构理论研究的代表作《乡土中国》、《生育制度》、《乡土重建》和《皇权与绅权》四篇著作，可供社会学工作或教学、研究者参考。', 0, 1);

-- ----------------------------
-- Table structure for hreturn
-- ----------------------------
DROP TABLE IF EXISTS `hreturn`;
CREATE TABLE `hreturn`  (
  `HBId` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书号',
  `HRId` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '读者卡号',
  `HBTime` date NOT NULL COMMENT '借书时间',
  `HBRTime` date NOT NULL COMMENT '还书时间',
  PRIMARY KEY (`HBId`, `HRId`, `HBTime`, `HBRTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hreturn
-- ----------------------------
INSERT INTO `hreturn` VALUES ('bk00001', '12345678910', '2019-10-29', '2019-11-12');
INSERT INTO `hreturn` VALUES ('bk00001', '12345678910', '2019-11-14', '2019-11-29');
INSERT INTO `hreturn` VALUES ('bk00003', '12345678910', '2019-11-14', '2019-11-15');
INSERT INTO `hreturn` VALUES ('bk00004', '12345678910', '2019-11-14', '2019-11-18');
INSERT INTO `hreturn` VALUES ('bk00005', '12345678910', '2019-11-14', '2019-11-28');
INSERT INTO `hreturn` VALUES ('bk00006', '12345678910', '2019-11-20', '2019-11-22');
INSERT INTO `hreturn` VALUES ('bk00007', '17251104243', '2019-11-21', '2019-12-06');
INSERT INTO `hreturn` VALUES ('bk00008', '12345678910', '2019-11-14', '2019-11-28');
INSERT INTO `hreturn` VALUES ('bk00009', '12345678910', '2019-11-14', '2019-11-28');
INSERT INTO `hreturn` VALUES ('bk00010', '17251104243', '2019-11-21', '2019-11-27');
INSERT INTO `hreturn` VALUES ('bk00012', '12345678910', '2019-11-20', '2019-11-22');
INSERT INTO `hreturn` VALUES ('bk00013', '12345678910', '2019-11-04', '2019-11-11');
INSERT INTO `hreturn` VALUES ('bk00014', '12345678910', '2019-11-20', '2019-11-22');
INSERT INTO `hreturn` VALUES ('bk00018', '12345678910', '2019-11-14', '2019-11-20');
INSERT INTO `hreturn` VALUES ('bk00018', '15412636974', '2019-11-22', '2019-11-23');
INSERT INTO `hreturn` VALUES ('bk00020', '15412636974', '2019-11-21', '2019-11-29');
INSERT INTO `hreturn` VALUES ('bk00040', '15412636974', '2019-11-22', '2019-12-07');
INSERT INTO `hreturn` VALUES ('bk00044', '12345678910', '2019-11-21', '2019-11-29');

-- ----------------------------
-- Table structure for nborrowing
-- ----------------------------
DROP TABLE IF EXISTS `nborrowing`;
CREATE TABLE `nborrowing`  (
  `NBId` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书号',
  `NRId` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '读者卡号',
  `NBTime` date NOT NULL COMMENT '借阅日期',
  `NBRTime` date NOT NULL COMMENT '应还日期',
  `NState` tinyint(4) NULL DEFAULT 0 COMMENT '借书状态',
  PRIMARY KEY (`NBId`, `NRId`, `NBTime`, `NBRTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nborrowing
-- ----------------------------
INSERT INTO `nborrowing` VALUES ('bk00002', '12345678910', '2019-11-14', '2019-11-16', 1);
INSERT INTO `nborrowing` VALUES ('bk00004', '12345678910', '2019-11-21', '2019-11-22', 1);
INSERT INTO `nborrowing` VALUES ('bk00006', '15412636974', '2019-11-22', '2019-11-30', 1);
INSERT INTO `nborrowing` VALUES ('bk00008', '15412636974', '2019-11-22', '2019-12-05', 1);
INSERT INTO `nborrowing` VALUES ('bk00019', '15412636974', '2019-11-22', '2019-11-30', 1);
INSERT INTO `nborrowing` VALUES ('bk00020', '12345678910', '2019-11-22', '2019-11-23', 2);
INSERT INTO `nborrowing` VALUES ('bk00020', '12345678910', '2019-11-22', '2019-11-29', 0);
INSERT INTO `nborrowing` VALUES ('bk00026', '12345678910', '2019-11-21', '2019-12-04', 1);
INSERT INTO `nborrowing` VALUES ('bk00036', '12345678910', '2019-11-21', '2019-11-29', 1);
INSERT INTO `nborrowing` VALUES ('bk00038', '12345678910', '2019-11-20', '2019-11-23', 2);
INSERT INTO `nborrowing` VALUES ('bk00044', '15412636974', '2019-11-22', '2021-11-26', 1);

-- ----------------------------
-- Table structure for qreserve
-- ----------------------------
DROP TABLE IF EXISTS `qreserve`;
CREATE TABLE `qreserve`  (
  `QBId` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书号',
  `QRId` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '读者卡号',
  `QSDate` date NOT NULL COMMENT '预约开始时间',
  `QEDate` date NOT NULL COMMENT '预约到书日期',
  `QState` tinyint(4) NOT NULL DEFAULT 0 COMMENT '预约状态',
  PRIMARY KEY (`QBId`, `QRId`, `QSDate`, `QEDate`) USING BTREE,
  INDEX `QRid`(`QRId`) USING BTREE,
  CONSTRAINT `QRid` FOREIGN KEY (`QRId`) REFERENCES `readers` (`RId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qreserve
-- ----------------------------
INSERT INTO `qreserve` VALUES ('bk00002', '17251104243', '2019-11-21', '2019-11-22', 0);
INSERT INTO `qreserve` VALUES ('bk00006', '12345678910', '2019-11-22', '2019-11-29', 0);
INSERT INTO `qreserve` VALUES ('bk00006', '15412636974', '2019-11-22', '2019-11-23', 1);
INSERT INTO `qreserve` VALUES ('bk00018', '15412636974', '2019-11-22', '2019-11-29', 1);
INSERT INTO `qreserve` VALUES ('bk00020', '12345678910', '2019-11-21', '2019-11-29', 1);
INSERT INTO `qreserve` VALUES ('bk00044', '15412636974', '2019-11-22', '2019-12-06', 1);

-- ----------------------------
-- Table structure for readers
-- ----------------------------
DROP TABLE IF EXISTS `readers`;
CREATE TABLE `readers`  (
  `RId` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '读者卡号',
  `RPasswd` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `RName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `RSex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `RUnit` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单位',
  `RDate` date NOT NULL COMMENT '注册日期',
  `Rnum` int(11) NULL DEFAULT 0 COMMENT '在借册数',
  `RTotal` int(255) NULL DEFAULT 200 COMMENT '最多在借册数',
  `RState` tinyint(255) NULL DEFAULT 0 COMMENT '挂失状态',
  PRIMARY KEY (`RId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of readers
-- ----------------------------
INSERT INTO `readers` VALUES ('09876543211', 'abc', '张三', '女', '学生', '2019-10-09', 0, 200, 0);
INSERT INTO `readers` VALUES ('12345678910', '123', '赵六', '男', '测试员', '2019-11-06', 7, 193, 0);
INSERT INTO `readers` VALUES ('12345678911', 'cdscs', '蔡徐坤', '男', '学生', '2019-10-27', 0, 200, 0);
INSERT INTO `readers` VALUES ('14725369899', 'eeee', '小草', '女', '教师', '2019-11-12', 0, 200, 1);
INSERT INTO `readers` VALUES ('15412636974', '789', '王服务', '男', '学生', '2019-11-12', 4, 196, 0);
INSERT INTO `readers` VALUES ('17251104243', 'abc', '小明', '男', '学生', '2019-10-25', 0, 200, 0);
INSERT INTO `readers` VALUES ('17251489631', '123', '校花', '女', '学生', '2019-10-28', 0, 200, 0);
INSERT INTO `readers` VALUES ('83774738923', '123', '习大大', '男', '学生', '2019-11-29', 0, 200, 2);

-- ----------------------------
-- Table structure for supermanager
-- ----------------------------
DROP TABLE IF EXISTS `supermanager`;
CREATE TABLE `supermanager`  (
  `Sid` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `SPasswd` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`Sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supermanager
-- ----------------------------
INSERT INTO `supermanager` VALUES ('root', 'admin');

-- ----------------------------
-- Table structure for tkind
-- ----------------------------
DROP TABLE IF EXISTS `tkind`;
CREATE TABLE `tkind`  (
  `TId` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书类型号',
  `TName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书类型名',
  PRIMARY KEY (`TId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tkind
-- ----------------------------
INSERT INTO `tkind` VALUES ('typebk01', '马列毛邓理论');
INSERT INTO `tkind` VALUES ('typebk02', '哲学、宗教');
INSERT INTO `tkind` VALUES ('typebk03', '社会科学总论');
INSERT INTO `tkind` VALUES ('typebk04', '政治、法律');
INSERT INTO `tkind` VALUES ('typebk05', '军事');
INSERT INTO `tkind` VALUES ('typebk06', '经济');
INSERT INTO `tkind` VALUES ('typebk07', '文化、科学、教育、体育');
INSERT INTO `tkind` VALUES ('typebk08', '语言、文字');
INSERT INTO `tkind` VALUES ('typebk09', '文学');
INSERT INTO `tkind` VALUES ('typebk10', '艺术');
INSERT INTO `tkind` VALUES ('typebk11', '历史、地理');
INSERT INTO `tkind` VALUES ('typebk12', '自然科学总论');
INSERT INTO `tkind` VALUES ('typebk13', '数理科学和化学');
INSERT INTO `tkind` VALUES ('typebk14', '天文学、地球科学');
INSERT INTO `tkind` VALUES ('typebk15', '生物科学');
INSERT INTO `tkind` VALUES ('typebk16', ' 医药、卫生');
INSERT INTO `tkind` VALUES ('typebk17', '农业科学');
INSERT INTO `tkind` VALUES ('typebk18', '工业技术');
INSERT INTO `tkind` VALUES ('typebk19', '交通运输');
INSERT INTO `tkind` VALUES ('typebk20', '航空、航天');
INSERT INTO `tkind` VALUES ('typebk21', '环境科学、劳动保护科学');
INSERT INTO `tkind` VALUES ('typebk22', '综合性图书');

-- ----------------------------
-- Table structure for uncomply
-- ----------------------------
DROP TABLE IF EXISTS `uncomply`;
CREATE TABLE `uncomply`  (
  `UId` int(11) NOT NULL AUTO_INCREMENT COMMENT '违约记录号',
  `UReason` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '违章原因',
  `UBId` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书号',
  `URId` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '读者卡号',
  `USDate` date NOT NULL COMMENT '借书日期',
  `UEDate` date NOT NULL COMMENT '还书日期',
  PRIMARY KEY (`UId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uncomply
-- ----------------------------
INSERT INTO `uncomply` VALUES (1, '该人损坏图书！', 'bk00020', '12345678910', '2019-11-22', '2019-11-23');
INSERT INTO `uncomply` VALUES (2, '该读者没有按照规定时间还书！！！', 'bk00038', '12345678910', '2019-11-20', '2019-11-23');

-- ----------------------------
-- Table structure for workers
-- ----------------------------
DROP TABLE IF EXISTS `workers`;
CREATE TABLE `workers`  (
  `WId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作号',
  `WPasswd` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `WName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `WSex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `WDate` date NOT NULL COMMENT '入职日期',
  `WUnit` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职务',
  PRIMARY KEY (`WId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of workers
-- ----------------------------
INSERT INTO `workers` VALUES ('wk1001', '123', '张三', '男', '2019-11-01', '采购岗位');
INSERT INTO `workers` VALUES ('wk1002', '978', '李四', '男', '2019-11-13', '阅览岗位');

SET FOREIGN_KEY_CHECKS = 1;
