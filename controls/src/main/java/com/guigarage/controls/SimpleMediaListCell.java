package com.guigarage.controls;

public class SimpleMediaListCell<T extends Media> extends MediaListCell<T> {

    private RoundImageView imageView;

    public SimpleMediaListCell() {
        imageView = new RoundImageView();
        setLeftContent(imageView);
        getStyleClass().add("simple-media-cell");
        itemProperty().addListener(e -> {
            titleProperty().unbind();
            descriptionProperty().unbind();
            imageView.imageProperty().unbind();
            if(getItem() != null) {
                titleProperty().bind(getItem().titleProperty());
                descriptionProperty().bind(getItem().descriptionProperty());
                imageView.imageProperty().bind(getItem().imageProperty());
            }
        });
    }
}
