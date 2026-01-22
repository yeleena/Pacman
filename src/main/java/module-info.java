module fr.univartois.butinfo.r304.pacman {
  exports fr.univartois.butinfo.r304.pacman;
  exports fr.univartois.butinfo.r304.pacman.controller;
  exports fr.univartois.butinfo.r304.pacman.model;
  exports fr.univartois.butinfo.r304.pacman.model.animated;
  exports fr.univartois.butinfo.r304.pacman.model.bonus;
  exports fr.univartois.butinfo.r304.pacman.model.factory;
  exports fr.univartois.butinfo.r304.pacman.model.interfaces;
  exports fr.univartois.butinfo.r304.pacman.model.map;
  exports fr.univartois.butinfo.r304.pacman.model.movesStrategy;
  exports fr.univartois.butinfo.r304.pacman.model.state;
  exports fr.univartois.butinfo.r304.pacman.view;

  

  requires javafx.fxml;
  requires javafx.graphics;
  requires transitive javafx.controls;
  requires java.base;
  requires fr.univartois.dpprocessor;
  opens fr.univartois.butinfo.r304.pacman to javafx.fxml;
  opens fr.univartois.butinfo.r304.pacman.controller to javafx.fxml;
}
