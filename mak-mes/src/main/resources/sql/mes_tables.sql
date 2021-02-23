-- MES Job
CREATE TABLE `mes_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Sequence ID',
  `job_code` varchar(128) NOT NULL COMMENT 'Job code',
  `purchase_order` varchar(128) DEFAULT NULL COMMENT 'Purchase order',
  `customer_name` varchar(256) NOT NULL COMMENT 'Customer name',
  `customer_address` varchar(2048) DEFAULT NULL COMMENT 'Customer address',
  `customer_phone` varchar(16) DEFAULT NULL COMMENT 'Customer phone',
  `status` int DEFAULT '0' COMMENT 'Job status - (0 - default, 1 - active, 2 - inactive, 3 - achieved)  ',
  `priority` int DEFAULT '3' COMMENT 'Job priority (1 - urgent, 2 - high, 3 - normal, 4 - low, 5 - trivial)',
  `start_at` date DEFAULT NULL COMMENT 'Job started at',
  `end_at` date DEFAULT NULL COMMENT 'Job should end at',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  `create_by` varchar(64) NOT NULL COMMENT 'Creator',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last updated time',
  `update_by` varchar(64) DEFAULT NULL COMMENT 'Updater',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_code_UNIQUE` (`job_code`),
  UNIQUE KEY `job_id_UNIQUE` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- MES Work Order
CREATE TABLE `mes_work_order` (
  `work_order_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `job_id` bigint NOT NULL COMMENT 'Reference to Job',
  `description` varchar(2048) NOT NULL COMMENT 'Description',
  `status` int DEFAULT '0' COMMENT 'Default = 0, Ready = 1, On-Going = 2, Completed = 3',
  `type` int DEFAULT '0' COMMENT 'Cabinet Vision = 0, Excel = 1',
  `sequence` int DEFAULT '1' COMMENT 'Sequence of the work order in the job',
  `cabinet_qty` int DEFAULT NULL COMMENT 'Number of cabinets in the work order',
  `part_qty` int DEFAULT NULL COMMENT 'Number of parts in the work order',
  `upload_file_path` varchar(2048) DEFAULT NULL COMMENT 'Link to store the uploaded file	',
  `process_file_path` varchar(2048) DEFAULT NULL COMMENT 'Link to processed file',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`work_order_id`),
  KEY `fk_work_order_job_idx` (`job_id`),
  CONSTRAINT `fk_work_order_job` FOREIGN KEY (`job_id`) REFERENCES `mes_job` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- MES Cabinet

CREATE TABLE `mes_cabinet` (
  `cabinet_id` bigint NOT NULL COMMENT 'Primary key',
  `work_order_id` bigint NOT NULL COMMENT 'Reference to Work Order',
  `code` varchar(32) DEFAULT NULL COMMENT 'Predefined code for the cabinet',
  `name` varchar(256) DEFAULT NULL COMMENT 'Name of cabinet',
  `description` varchar(256) DEFAULT NULL COMMENT 'Description of the cabinet',
  `asm_class` varchar(128) DEFAULT NULL COMMENT 'Assembly class of the cabinet (e.g: base, upper)',
  `asm_type` varchar(128) DEFAULT NULL COMMENT 'Type of them assembly (e.g: standard, corner, filler…)',
  `width` decimal(20,0) DEFAULT NULL COMMENT 'A value representing the Width of the Cabinet/Assembly.',
  `depth` decimal(20,0) DEFAULT NULL COMMENT 'A value representing the Depth of the Cabinet/Assembly.',
  `height` decimal(20,0) DEFAULT NULL COMMENT 'A value representing the Height of the Cabinet/Assembly.',
  `product_code` varchar(45) DEFAULT NULL COMMENT 'Product code which this assembly/cabinet belong to',
  `product_name` varchar(2048) DEFAULT NULL COMMENT 'Product name which this assembly/cabinet belong to',
  `room_num` varchar(45) DEFAULT NULL COMMENT 'Linking the Cabinet/Assembly to a Room',
  PRIMARY KEY (`cabinet_id`),
  KEY `fk_cabinet_work_order_idx` (`work_order_id`),
  CONSTRAINT `fk_cabinet_work_order` FOREIGN KEY (`work_order_id`) REFERENCES `mes_work_order` (`work_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- MES Part

CREATE TABLE `mes_part` (
  `part_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `work_order_id` bigint DEFAULT NULL COMMENT 'FK to work order table',
  `cabinet_id` bigint DEFAULT NULL COMMENT 'In some case, part do not reference to Cabinet table',
  `part_sequence` int DEFAULT NULL COMMENT 'Part sequence',
  `barcode` varchar(256) NOT NULL COMMENT 'Part generated barcode',
  `name` varchar(256) NOT NULL COMMENT 'Part name',
  `short_name` varchar(256) DEFAULT NULL COMMENT 'Part short name',
  `cut_width` decimal(20,0) NOT NULL COMMENT 'Part width as cut on machine',
  `cut_length` decimal(20,0) NOT NULL COMMENT 'Part length as cut on machine',
  `banded_width` decimal(20,0) DEFAULT NULL COMMENT 'Finished part width after banding',
  `banded_length` decimal(20,0) DEFAULT NULL COMMENT 'Finished part length after banding',
  `qty` int DEFAULT '1' COMMENT 'A value representing the Quantity of the part',
  `primary_edge` int DEFAULT NULL COMMENT 'Primary part edge that rests against the point-to-point pins (1 = Left, 2 = Top, 3 = Right, 4 = Bottom)',
  `workflow` varchar(256) DEFAULT NULL COMMENT 'The work stations that part need to go through',
  `panel_material` varchar(256) DEFAULT NULL COMMENT 'The name for the sheet material',
  `material_thickness` decimal(20,0) DEFAULT NULL COMMENT 'A value representing the Thickness for this sheet of Material',
  `banding_flags` varchar(45) DEFAULT NULL COMMENT '6NNENEN 6 sided part from face clockwise stroke(N = None, I = Interior, E = Exterior, D = Door)',
  `front_program` varchar(45) DEFAULT NULL COMMENT 'Program name for the part front face operations',
  `back_program` varchar(45) DEFAULT NULL COMMENT 'Program name for the part back face operations',
  `front_orientation` varchar(45) DEFAULT NULL COMMENT 'Indicates the arrow position for face program (e.g: arrow flip and Face Up/Down flag)',
  `back_orientation` varchar(45) DEFAULT NULL COMMENT 'Indicates the arrow position for back program (e.g: arrow flip and Face Up/Down flag)',
  `primary_machine_name` varchar(128) DEFAULT NULL COMMENT 'Machine used for primary output',
  `edge_material` varchar(1028) DEFAULT NULL COMMENT 'Edge material (e.g: D:1m23_101T, E:1m23_388EV)',
  `image_name` varchar(128) DEFAULT NULL COMMENT 'File title of part/label image',
  `image_in_byte` blob COMMENT 'Binary graphic for this part',
  PRIMARY KEY (`part_id`),
  UNIQUE KEY `barcode_UNIQUE` (`barcode`),
  KEY `fk_part_work_order_idx` (`work_order_id`),
  CONSTRAINT `fk_part_work_order` FOREIGN KEY (`work_order_id`) REFERENCES `mes_work_order` (`work_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Mes Pattern

CREATE TABLE `mes_pattern` (
  `pattern_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `work_order_id` bigint DEFAULT NULL COMMENT 'Reference to work order',
  `run_tag` varchar(45) DEFAULT NULL COMMENT 'Each optimizer will generate run number, pattern for which run',
  `material_name` varchar(256) NOT NULL COMMENT 'Material for this pattern',
  `primary_program` varchar(128) DEFAULT NULL COMMENT 'Primary program',
  `primary_barcode` varchar(128) DEFAULT NULL COMMENT 'Primary barcode',
  `image_in_byte` blob NOT NULL COMMENT 'Image for this pattern',
  `sixth_face` tinyint DEFAULT '0' COMMENT 'Is this 6th face',
  `sixth_face_program` varchar(128) DEFAULT NULL COMMENT '6th face program',
  `sixth_face_barcode` varchar(128) DEFAULT NULL COMMENT '6th face barcode',
  PRIMARY KEY (`pattern_id`),
  KEY `fk_pattern_work_order_idx` (`work_order_id`),
  CONSTRAINT `fk_pattern_work_order` FOREIGN KEY (`work_order_id`) REFERENCES `mes_work_order` (`work_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Mes Panel Layout

CREATE TABLE `mes_panel_layout` (
  `panel_layout_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `work_order_id` bigint NOT NULL COMMENT 'Reference to Work Order table',
  `part_id` bigint NOT NULL COMMENT 'Reference to Part table',
  `pattern_id` bigint NOT NULL COMMENT 'Reference to Pattern table',
  `part_index_on_pattern` int NOT NULL COMMENT 'Position of part which is on the pattern',
  PRIMARY KEY (`panel_layout_id`),
  KEY `fk_panel_layout_work_order_idx` (`work_order_id`),
  KEY `fk_panel_layout_pattern_idx` (`pattern_id`),
  KEY `fk_panel_layout_part_idx` (`part_id`),
  CONSTRAINT `fk_panel_layout_part` FOREIGN KEY (`part_id`) REFERENCES `mes_part` (`part_id`),
  CONSTRAINT `fk_panel_layout_pattern` FOREIGN KEY (`pattern_id`) REFERENCES `mes_pattern` (`pattern_id`),
  CONSTRAINT `fk_panel_layout_work_order` FOREIGN KEY (`work_order_id`) REFERENCES `mes_work_order` (`work_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;