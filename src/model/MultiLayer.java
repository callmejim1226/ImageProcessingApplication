package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a multilayer model that has its list of layers. Each multilayer does not
 * have layers in the beginning, it starts with an empty list and changes in the process.
 */
public class MultiLayer {
  private List<Layer> layers;

  /**
   * Constructs the multilayer by initializing an empty list of layers.
   */
  public MultiLayer() {
    this.layers = new ArrayList<Layer>();
  }

  /**
   * Gets the current state of an MultiLayer by outputting its list of layers.
   *
   * @return the list of layers the multilayer currently has
   */
  public List<Layer> getCurrentState() {
    return this.layers;
  }

  /**
   * Produces the size of the list of layers MultiLayer currently has.
   *
   * @return the size of list of layers of MultiLayer
   */
  public int getMultiLayerSize() {
    return this.layers.size();
  }

  /**
   * Gets a layer of a given index from MultiLayer's list of layers.
   *
   * @param index the index of the layer in the list
   * @return the layer with the specified index
   * @throws IllegalArgumentException if the index exceeds or equals to the size of the list
   */
  public Layer getLayer(int index) {
    if (index >= getMultiLayerSize()) {
      throw new IllegalArgumentException("Invalid index");
    }
    return this.layers.get(index);
  }

  /**
   * Sets the state of the multilayer to the other list of layers.
   *
   * @param other the list of layers to change to
   * @throws IllegalArgumentException if list of layers is null
   */
  public void setState(List<Layer> other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("List of layers is null");
    }
    this.layers = other;
  }

  /**
   * Removes the layer from the list of layers in multilayer. If the layer does not exist,
   * does nothing.
   *
   * @param removeLayer a layer to remove from the layer
   * @throws IllegalArgumentException if a given layer is null
   */
  public void removeLayer(Layer removeLayer) throws IllegalArgumentException {
    if (removeLayer == null) {
      throw new IllegalArgumentException("Layer is null");
    }
    this.layers.remove(removeLayer);
  }

  /**
   * Determines whether all layers are invisible.
   *
   * @return whether all layers are invisible.
   */
  public boolean isAllInvisibleLayer() {
    boolean result = true;
    for (Layer layer : layers) {
      result = result && !layer.isTopMostVisible();
    }
    return result;
  }

  /**
   * Gets the top most visible layer, assuming there is visible layer exists.
   *
   * @return the top most visible layer.
   * @throws IllegalArgumentException if MultiLayer is empty or invisible completely
   */
  public Layer getTopMostLayer() throws IllegalArgumentException {
    if (getMultiLayerSize() == 0 || isAllInvisibleLayer()) {
      throw new IllegalArgumentException("Empty Multilayer");
    }
    int layerIndex = 0;
    while (!layers.get(layerIndex).isTopMostVisible()) {
      layerIndex += 1;
    }
    return layers.get(layerIndex);
  }

  /**
   * Adds a new layer to the layers in MultiLayers.
   *
   * @param l the layer to add to the layers of Multilayers
   */
  public void addLayer(Layer l) throws IllegalArgumentException {
    if (l == null) {
      throw new IllegalArgumentException("The layer is null");
    }
    this.layers.add(l);
  }
}
