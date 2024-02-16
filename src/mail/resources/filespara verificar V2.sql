SELECT * FROM bytsscom_bytsig.hoja_ruta where num_hoja_ruta like '%23014676%';

SELECT * FROM bytsscom_bytsig.hoja_ruta WHERE id_hoja_ruta=91984;

-------------------------------------------------------------------------------------------------------------

SELECT * FROM bytsscom_bytsig.hoja_ruta_movimiento WHERE id_hoja_ruta=91984;

SELECT * FROM bytsscom_bytsig.hoja_ruta_movimiento WHERE id_hoja_ruta=75935 and id_corr_hr_mov BETWEEN 43 AND 54;
  DELETE FROM bytsscom_bytsig.hoja_ruta_movimiento WHERE id_hoja_ruta=75935 and id_corr_hr_mov BETWEEN 43 AND 54;
  
-------------------------------------------------------------------------------------------------------------

SELECT * FROM bytsscom_bytsig.registro_hoja_ruta_det WHERE id_hoja_ruta=91984 order by 7;


SELECT * FROM bytsscom_bytsig.registro_hoja_ruta_det WHERE id_registro_hr=144158;

SELECT * FROM bytsscom_bytsig.registro_hoja_ruta_det WHERE id_registro_hr=118535 and id_corr_hr_mov BETWEEN 43 AND 54;
   DELETE FROM bytsscom_bytsig.registro_hoja_ruta_det WHERE id_registro_hr=118535 and id_corr_hr_mov BETWEEN 43 AND 54;
  
-------------------------------------------------------------------------------------------------------------

SELECT * FROM bytsscom_bytsig.registro_hoja_ruta WHERE id_hoja_ruta_actual=91984;

UPDATE bytsscom_bytsig.registro_hoja_ruta
  SET id_corr_actual = 14
  WHERE id_registro_hr =144158;


  UPDATE bytsscom_bytsig.registro_hoja_ruta
  SET id_corr_actual = 14
  WHERE id_registro_hr =144158 and id_registro_hr_corr = 1;